package com.github.sunnus3.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;

/**
 * @author: zhangwei
 * @date: 2019-07-12/13:52
 */
@SpringBootApplication
public class AopApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class);
    }

    @Autowired
    private Service service;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("autowired:" + System.identityHashCode(service));

        Method method1 = Service.class.getDeclaredMethod("method1");
        method1.setAccessible(true);
        method1.invoke(service);

        Method method2 = Service.class.getDeclaredMethod("method2");
        method2.setAccessible(true);
        method2.invoke(service);
    }
}
