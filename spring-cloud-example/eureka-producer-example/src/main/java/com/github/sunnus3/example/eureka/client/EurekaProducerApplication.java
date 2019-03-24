package com.github.sunnus3.example.eureka.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: zhangwei
 * @date: 09:36/2019-02-17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaProducerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaProducerApplication.class)
                .run(args);
    }
}
