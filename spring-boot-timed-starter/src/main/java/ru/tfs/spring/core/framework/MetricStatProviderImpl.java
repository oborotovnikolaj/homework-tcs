package ru.tfs.spring.core.framework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class MetricStatProviderImpl implements MetricStatProvider {

    @Value("${metrics.limit}")
    private int methodInvocationMetricLimit;

    private final Map<String, MethodMetricStat> methodMetricStatMap;
    private final Map<String, List<MethodInvocationMetric>> methodInvocationMetricMap;

    public MetricStatProviderImpl() {
        this.methodMetricStatMap = new ConcurrentHashMap<>();
        this.methodInvocationMetricMap = new ConcurrentHashMap<>();
    }

    @Override
    public List<MethodMetricStat> getTotalStat() {
        return new ArrayList<>(methodMetricStatMap.values());
    }

    @Override
    public List<MethodMetricStat> getTotalStatForPeriod(LocalDateTime from, LocalDateTime to) {
        return methodInvocationMetricMap.values().stream()
                .map(invocations -> MethodMetricStat.creatMethodMetricStat(getFilteredInvocation(from, to, invocations)))
                .collect(Collectors.toList());
    }

    private List<MethodInvocationMetric> getFilteredInvocation(
            LocalDateTime from,
            LocalDateTime to,
            List<MethodInvocationMetric> invocations) {

        return invocations.stream()
                .filter(invocation -> isBetween(from, to, invocation.getInvocationTime()))
                .collect(Collectors.toList());
    }

    private boolean isBetween(LocalDateTime from, LocalDateTime to, LocalDateTime dateToCheck) {
        return dateToCheck.isAfter(from) && dateToCheck.isBefore(to);
    }

    @Override
    public MethodMetricStat getTotalStatByMethod(String method) {
        return methodMetricStatMap.get(method);
    }

    @Override
    public MethodMetricStat getTotalStatByMethodForPeriod(String method, LocalDateTime from, LocalDateTime to) {
        return MethodMetricStat.creatMethodMetricStat(
                getFilteredInvocation(from, to, methodInvocationMetricMap.get(method))
        );
    }

    @Override
    public void clean() {
        methodMetricStatMap.clear();
        methodInvocationMetricMap.clear();
    }


    void update(MethodInvocationMetric methodInvocationMetric) {
        List<MethodInvocationMetric> methodMetricStats = methodInvocationMetricMap.get(methodInvocationMetric.getMethod());
        if (methodMetricStats == null) {
            methodMetricStats = Collections.synchronizedList(new ArrayList<MethodInvocationMetric>());
            List<MethodInvocationMetric> previous =
                    methodInvocationMetricMap.putIfAbsent(methodInvocationMetric.getMethod(), methodMetricStats);
            if (previous != null) {
                methodMetricStats = previous;
            }
        }
        synchronized (methodMetricStats) {
            if (methodMetricStats.size() == methodInvocationMetricLimit) {
                methodMetricStats.remove(0);
            }
            methodMetricStats.add(methodInvocationMetric);

            methodMetricStatMap.put(
                    methodInvocationMetric.getMethod(),
                    MethodMetricStat.creatMethodMetricStat(methodMetricStats)
            );
        }
    }
}
