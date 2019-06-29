package com.github.arugal.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangwei
 * @date: 2019-06-29/21:30
 */
@SpringBootApplication
public class DockerApplication {

    /**
     * @See https://dmp.fabric8.io/
     * @param args
     */

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }
}
