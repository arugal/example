package com.github.sunnus3.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * @author: zhangwei
 * @date: 13:36/2019-02-20
 */
@RestController
@RequestMapping("/redis")
public class RedisController {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/set/{key}/{value}")
    public boolean set(@PathVariable String key, @PathVariable String value){
        redisTemplate.opsForValue().set(key, value, Duration.ofDays(1));
        return true;
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key){
        String result = redisTemplate.opsForValue().get(key);
        return result == null ? "Null" : result;
    }

}
