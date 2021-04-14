package org.tze.connectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.tze.connectservice.server.NettyServer;

@SpringBootApplication
@EnableEurekaClient
public class ConnectServiceApplication {

    public static void main(String[] args) {
        SpringApplication application=new SpringApplication(ConnectServiceApplication.class);
        application.run(args);

        //启动1883
        new NettyServer().startup();
    }

}
