package com.github.arugal.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

/**
 * @author: zhangwei
 * @date: 2019-06-30/23:41
 */
@SpringBootApplication
public class RedisLuaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisLuaApplication.class, args);
    }

    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;

    @Autowired
    private DefaultRedisScript<Long> demoRedisScript;

    @Bean
    public ApplicationRunner runner() {
        return (args) -> {
            Long result = longRedisTemplate.execute(demoRedisScript, Collections.singletonList("key3"), 3L);
            System.out.println(result);
        };
    }
}
