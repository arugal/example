package com.github.arugal.spring.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author zhangwei
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class StandaloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StandaloneApplication.class, args);
	}

	@Scheduled(cron = "1 * * * * ?")
	public void scheduledTask1(){
		log.info("invoke scheduledTask1");
	}

	@Scheduled(initialDelay = 1000, fixedDelay = 1000 * 2)
	public void scheduledTask2(){
		log.info("invoke scheduledTask2");
	}
}
