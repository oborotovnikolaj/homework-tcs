package ru.tfs.spring.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.tfs.spring.core.framework.Timed;

@Configuration
@ConditionalOnClass({Timed.class})
@ConditionalOnProperty(prefix= "metrics", name = {"enable", "limit"})
@EnableConfigurationProperties(TimedProperty.class)
public class TimedAutoConfiguration {

    @Autowired
    TimedProperty timedProperty;

}
