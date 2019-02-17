package com.github.sununs3.example.ribbon;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author: zhangwei
 * @date: 22:31/2019-02-16
 */
@SpringBootApplication
public class RibbonApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RibbonApplication.class)
                .run(args);
    }
}
