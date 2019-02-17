package com.github.sunnus3.example.eureka.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangwei
 * @date: 09:36/2019-02-17
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {


    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name){
        return "Hello "+name;
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientApplication.class)
                .run(args);
    }
}
