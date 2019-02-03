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
 * @date: 00:17/2019-02-03
 */
@SpringBootApplication
public class NacosConsumerApplication {

    private static final Logger logger = LoggerFactory.getLogger(NacosConsumerApplication.class);

    @Reference(version = "1.0.0")
    private SayService sayService;

    @Bean
    public ApplicationRunner runner(){
        return args -> {
            logger.info(sayService.say("consumer"));
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(NacosConsumerApplication.class)
                .run(args);
    }
}
