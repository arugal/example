package com.github.sunnus3.example.dubbo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author: zhangwei
 * @date: 23:34/2019-02-02
 */
@SpringBootApplication
public class AutoProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AutoProviderApplication.class)
                .run(args);
    }
}
