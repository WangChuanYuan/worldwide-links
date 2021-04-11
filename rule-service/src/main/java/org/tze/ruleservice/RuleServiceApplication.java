package org.tze.ruleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableEurekaClient
public class RuleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuleServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate initRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 解决中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

}
