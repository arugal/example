package com.github.arugal.example.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangwei
 */
@Setter
@Getter
@ConfigurationProperties("kafka.topic")
public class KafkaTopicProperties implements java.io.Serializable{
    private static final long serialVersionUID = -4557019332306857095L;

    private String groupId;
    private String[] topicName;
}
