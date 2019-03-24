package com.github.sunnus3.example.hystrix;

import com.github.sunnus3.example.hystrix.order.GetOrderCircuitBreakerCommand;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author: zhangwei
 * @date: 15:52/2019-02-17
 */
@SpringBootApplication
public class HystrixApplication {

    @Bean
    public ApplicationRunner circuitBreakerRunner(){
        return  args -> {
            for(int i = 0; i < 25; i++){
                Thread.sleep(500);
                HystrixCommand<String> command = new GetOrderCircuitBreakerCommand("CircuitBreaker");
                String result = command.execute();
                System.out.println("call times:"+(i + 1)+" result:"+result+" isCircuitBreakerOpen:"+command.isCircuitBreakerOpen());
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}
