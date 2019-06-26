package com.github.arugal.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author: zhangwei
 * @date: 2019-06-22/14:40
 */
@SpringBootApplication
@EnableKafka
public class KafkaApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String msg){
        kafkaTemplate.send("tutorialspoint", msg);
    }

    @KafkaListener(topics = "tutorialspoint", groupId = "group-id")
    public void listen(String message){
        System.out.println("Received Message in group - group-id:"+message);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        sendMessage("Hi Welcome to Spring For Apache Kafka");
    }
}
