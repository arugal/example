package com.github.arugal.example.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author: zhangwei
 * @date: 2019-06-18/22:32
 */
@Configuration
@EnableConfigurationProperties(KafaTopicProperties.class)
public class KafkaTopicConfiguration {

    private final KafaTopicProperties properties;

    public KafkaTopicConfiguration(KafaTopicProperties properties) {
        this.properties = properties;
    }

    @Bean
    public String[] kafkaTopicNames() {
        return new String[]{kafkaTopicName()};
    }

    @Bean
    public String kafkaTopicName() {
        return properties.getTopicName();
    }

    @Bean
    public String topicGroupId() {
        return properties.getGroupId();
    }
}
