package com.github.arugal.example.spring.cache.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zhangwei
 * @date: 22:23/2019-03-18
 */
@Component
public class KeyValueStore {

    private static final Map<String, String> storeMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(KeyValueStore.class);

    public int size() {
        return storeMap.size();
    }

    public boolean isEmpty() {
        return storeMap.isEmpty();
    }

    @Cacheable(value = "", key = "getTargetClass()+'.'+#key")
    public String get(Object key) {
        logger.info("get key{}", key);
        return storeMap.get(key);
    }

    @CachePut(value = "", key = "getTargetClass()+'.'+#key")
    public String put(String key, String value) {
        return storeMap.put(key, value);
    }

    @CacheEvict(value = "", key = "getTargetClass()+'.'+#key")
    public String remove(Object key) {
        return null;
    }


    @CacheEvict(value = "", allEntries = true)
    public void clear() {
        storeMap.clear();
    }
}
