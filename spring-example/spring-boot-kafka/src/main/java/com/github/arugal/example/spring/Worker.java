package com.github.arugal.example.spring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 2019-06-18/22:37
 */
@Service
public class Worker implements ApplicationRunner {

    private static final ScheduledExecutorService POOL = Executors.newScheduledThreadPool(1);

    private static Logger logger = LoggerFactory.getLogger(Worker.class);

    @Autowired
    private KafkaTopicConfiguration configuration;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @KafkaListener(topics = "#{kafkaTopicNames}", groupId = "#{topicGroupId}")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        logger.info("processMessage, topic = {}, msg = {}", record.topic(), record.value());
    }


    @Override
    public void run(ApplicationArguments args) {
        POOL.scheduleAtFixedRate(() -> {
            logger.info("kafka sendMessage start");
            ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(configuration.kafkaTopicName(),
                    Thread.currentThread().getName());

            future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    logger.error("kafka sendMessage error, ex = {}, topic = {}", ex, configuration.kafkaTopicName());
                }

                @Override
                public void onSuccess(SendResult<Integer, String> result) {
                    logger.error("kafka sendMessage success topic = {}", configuration.kafkaTopicName());
                }
            });

        }, 1, 5, TimeUnit.SECONDS);
    }
}
