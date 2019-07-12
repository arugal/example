package com.github.sunnus3.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;

/**
 * @author: zhangwei
 * @date: 16:56/2019-01-25
 */
@SpringBootApplication
public class WebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Autowired
    private FooController fooController;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(System.identityHashCode(fooController));
        Method method = FooController.class.getDeclaredMethod("foo", Integer.class, String.class);
        method.setAccessible(true);
        method.invoke(fooController, 1, "a");

        method = FooController.class.getDeclaredMethod("foo1", Integer.class, String.class);
        method.setAccessible(true);
        method.invoke(fooController, 1, "a");
    }
}
