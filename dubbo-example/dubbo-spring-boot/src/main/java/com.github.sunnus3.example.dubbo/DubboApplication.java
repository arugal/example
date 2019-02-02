package com.github.sunnus3.example.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author: zhangwei
 * @date: 22:04/2019-02-02
 */
@SpringBootApplication
public class DubboApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DubboApplication.class, args);
        Environment environment = applicationContext.getEnvironment();
        System.out.println(environment.getProperty("dubbo.key"));
    }
}
