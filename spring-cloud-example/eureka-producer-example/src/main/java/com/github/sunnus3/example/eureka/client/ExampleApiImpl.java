package com.github.sunnus3.example.eureka.client;

import com.github.sunnus3.example.eureka.ExampleApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author: zhangwei
 * @date: 14:34/2019-02-17
 */
@RestController
public class ExampleApiImpl implements ExampleApi {

    private final Random random = new Random();

    @Value("${server.port}")
    private int port;

    @Override
    public String sayHello(String name) {

        return String.format("Hello %s! by:%d", name, port);
    }

    @Override
    public String randomException() {
        if(random.nextInt(5) % 2 == 0){
            throw new RuntimeException("random exception!");
        }
        return "ok!";
    }
}
