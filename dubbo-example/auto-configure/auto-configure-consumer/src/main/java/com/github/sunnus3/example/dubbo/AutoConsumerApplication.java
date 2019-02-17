package com.github.sunnus3.example.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author: zhangwei
 * @date: 23:41/2019-02-02
 */
@SpringBootApplication
public class AutoConsumerApplication {

    private static final Logger logger = LoggerFactory.getLogger(AutoConsumerApplication.class);

    @Reference(version = "${auto.dubbo.version}", url = "dubbo://localhost:25880")
    private SayService sayService;

    @Bean
    public ApplicationRunner runner(){
        return args -> logger.info(sayService.say("consumer"));
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AutoConsumerApplication.class)
                .run(args);
    }
}
