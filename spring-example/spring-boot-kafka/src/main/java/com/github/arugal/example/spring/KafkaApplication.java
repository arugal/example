package com.github.arugal.example.spring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author: zhangwei
 * @date: 2019-06-22/14:40
 */
@SpringBootApplication
@EnableKafka
public class KafkaApplication implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("kafka sendMessage success topic = {}, data = {}", topic, data);
            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
            }
        });
    }

    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "#{topicGroupId}")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received Message in group - group-id:" + record.value());
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        sendMessage("flink-test","Hi Welcome to Spring For Apache Kafka");
    }
}
