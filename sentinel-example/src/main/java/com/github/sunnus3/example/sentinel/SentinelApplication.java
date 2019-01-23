package com.github.sunnus3.example.sentinel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangwei
 * @date: 19:20/2019-01-19
 */
@SpringBootApplication
public class SentinelApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
