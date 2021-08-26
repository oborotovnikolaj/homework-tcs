package ru.tfs.spring.core.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TimedBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(TimedBeanPostProcessor.class);

    private final Map<String, Class> timedBeans = new HashMap<>();

    @Value("${metrics.enable}")
    private boolean metricsEnable;

    @Autowired
    MetricStatProviderImpl metricStatProvider;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (timedBeans.get(beanName) != null) {
            return bean;
        }

        if (metricsEnable) {
            Class<?> beanClass = bean.getClass();
            Timed classAnnotation = beanClass.getAnnotation(Timed.class);
            if (classAnnotation != null && classAnnotation.isEnable()) {
                timedBeans.put(beanName, beanClass);
            } else {
                for (Method method : beanClass.getMethods()) {
                    Timed methodAnnotation = method.getAnnotation(Timed.class);
                    if (methodAnnotation != null && methodAnnotation.isEnable()) {
                        timedBeans.put(beanName, beanClass);
                    }
                }
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> originalBean = timedBeans.get(beanName);

        if (originalBean != null) {

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(originalBean);
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {

                Method originalMethod = Arrays.stream(originalBean.getMethods())
                        .filter(method::equals)
                        .findFirst().orElse(null);


                if (originalMethod != null && needToCreateProxy(originalBean, originalMethod)) {
                    MethodInvocationMetric methodMetric = new MethodInvocationMetric();
                    methodMetric.setMethod(originalBean.getName() + "#" + method.getName());
                    methodMetric.setInvocationTime(LocalDateTime.now());

                    log.info("invoke {}#{}: with args {}",
                            originalBean.getName(),
                            method.getName(),
                            Arrays.stream(args)
                                    .map(Object::toString)
                                    .collect(Collectors.joining(","))
                    );
                    Object invoke = proxy.invoke(bean, args);
                    log.info("result of {}#{}: {}", originalBean.getSimpleName(), method.getName(), invoke != null ? invoke.toString() : "void");

                    methodMetric.setTotalTime((int) Duration.between(methodMetric.getInvocationTime(), LocalDateTime.now()).toMillis());
                    metricStatProvider.update(methodMetric);

                    return invoke;
                }
                return method.invoke(bean, args);
            });

            return enhancer.create();
        } else {
            return bean;
        }
    }

    private boolean needToCreateProxy(Class<?> clazz, Method method) {
        Timed clazzAnnotation = clazz.getAnnotation(Timed.class);
        Timed methodAnnotation = method.getAnnotation(Timed.class);

        return (clazzAnnotation != null && clazzAnnotation.isEnable()) ||
                (methodAnnotation != null && methodAnnotation.isEnable());
    }
}
