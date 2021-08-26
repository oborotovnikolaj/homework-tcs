package ru.tfs.read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@EnableWebMvc
//@EnableWebFlux
public class TcsReadApiApplication {

//	@Bean
//	public HttpMessageConverters getHttpMessageConverters() {
//		return new HttpMessageConverters();
//	}

	public static void main(String[] args) {
		SpringApplication.run(TcsReadApiApplication.class, args);
	}

}
