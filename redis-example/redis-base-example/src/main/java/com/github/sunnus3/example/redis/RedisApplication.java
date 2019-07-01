package com.github.sunnus3.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: zhangwei
 * @date: 13:14/2019-02-20
 */
@SpringBootApplication
public class RedisApplication {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }


    public ApplicationRunner execRin(){
        return (arg) ->{
           redisTemplate.execute(new RedisCallback<Object>() {

               @Override
               public Object doInRedis(RedisConnection connection) throws DataAccessException {
                   return null;
               }
           });
        };
    }
}
