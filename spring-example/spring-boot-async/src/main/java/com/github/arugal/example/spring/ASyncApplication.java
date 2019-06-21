package com.github.arugal.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 2019-06-21/14:36
 */
@SpringBootApplication
@SpringBootConfiguration
@EnableAsync
public class ASyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(ASyncApplication.class, args);
    }

    @Autowired
    private ASyncWoker aSyncWoker;

    @Bean
    public ApplicationRunner runner() {
        return (arg) -> {
            aSyncWoker.aSyncPrint();
        };
    }

    @Bean
    public Executor scheduledExecutor() {
        return new ScheduledExecutor(Executors.newSingleThreadScheduledExecutor(new ScheduledThreadFactory()));
    }

    @Bean
    public ASyncWoker aSyncWoker() {
        return new ASyncWoker();
    }

    public static class ASyncWoker {

        @Async("scheduledExecutor")
        public void aSyncPrint() {
            System.out.println("currentName:" + Thread.currentThread().getName());
        }
    }

    public static class ScheduledThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ScheduledThread");
        }
    }

    public static class ScheduledExecutor implements Executor {

        private ScheduledExecutorService scheduledExecutorService;

        public ScheduledExecutor(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
        }

        @Override
        public void execute(Runnable command) {
            scheduledExecutorService.schedule(command, 5, TimeUnit.SECONDS);
        }
    }

}
