package ru.tfs.spring.core;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = {"ru.tfs.spring.core.workers", "ru.tfs.spring.core.framework"})
@PropertySource("classpath:application.properties")
public class TestConfiguration {

    @Bean
    public BeanFactoryPostProcessor getPP() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocations(new ClassPathResource("/application.properties"));
        return configurer;
    }

}
