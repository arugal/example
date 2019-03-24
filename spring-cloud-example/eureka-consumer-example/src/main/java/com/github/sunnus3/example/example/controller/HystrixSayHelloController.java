package com.github.sunnus3.example.example.controller;

import com.github.sunnus3.example.eureka.ExampleApi;
import com.github.sunnus3.example.example.fegin.HystrixExampleApiRemote;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author: zhangwei
 * @date: 18:48/2019-02-18
 */
@RestController
public class HystrixSayHelloController implements ExampleApi {

    private static final Logger logger = LoggerFactory.getLogger(HystrixSayHelloController.class);

    private final Random random = new Random();

    @Autowired
    private HystrixExampleApiRemote hystrixSayHelloRemote;

    @Override
    public String sayHello(String name) {
        return hystrixSayHelloRemote.sayHello(name);
    }

    @Override
    public String randomException() {
        return hystrixSayHelloRemote.randomException();
    }

    @GetMapping("/exe/{command}")
    @HystrixCommand(commandKey = "ECommand", fallbackMethod = "commandFallback")
    public String command(@PathVariable String command) {
        int randomNum = random.nextInt(5);
        try {
            logger.info("command sleep:{}", randomNum);
            Thread.sleep(randomNum * 1000);
        } catch (InterruptedException e) {
            // ignore
        }
        if (randomNum % 2 == 0) {
            throw new RandomException("random exception");
        }
        return String.format("->: %s", command);
    }

    public String commandFallback(String command, Throwable throwable) {
        logger.error(command, throwable);
        return String.format("->: fallback by %s/%s", throwable.getClass().getSimpleName(), throwable.getMessage());
    }

    class RandomException extends RuntimeException{

        public RandomException(String message) {
            super(message);
        }
    }
}
