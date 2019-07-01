package com.github.arugal.example.redis;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author: zhangwei
 * @date: 2019-06-30/22:59
 */
@SpringBootConfiguration
public class RedisConfigurable extends CachingConfigurerSupport {

    @Bean
    public DefaultRedisScript<String> demoRedisScript() {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/demo.lua")));
        return defaultRedisScript;
    }

    @Bean
    public RedisTemplate<String, Long> longRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);

        //序列化 值时使用此序列化方法
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
