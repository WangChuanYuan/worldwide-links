package org.tze.ruleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RuleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuleServiceApplication.class, args);
    }

}
