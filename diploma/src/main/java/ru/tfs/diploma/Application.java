package ru.tfs.diploma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableWebMvc
public class Application {

//    @Bean
//    public SpringLiquibase getSpringLiquibase(@Qualifier("dataSource") DataSource dataSource) {
//        SpringLiquibase springLiquibase = new SpringLiquibase();
//        springLiquibase.setChangeLog("classpath:liquibase-changeLog.xml");
//        springLiquibase.setDataSource(dataSource);
//        return springLiquibase;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
