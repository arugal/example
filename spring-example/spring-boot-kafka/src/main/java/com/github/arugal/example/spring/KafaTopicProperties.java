package com.github.arugal.example.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author: zhangwei
 * @date: 2019-06-18/22:28
 */
@ConfigurationProperties("kafka.topic")
public class KafaTopicProperties implements Serializable {

    private static final long serialVersionUID = -8687175493345198068L;

    @Getter
    @Setter
    private String groupId;
    @Getter
    @Setter
    private String topicName;
}
