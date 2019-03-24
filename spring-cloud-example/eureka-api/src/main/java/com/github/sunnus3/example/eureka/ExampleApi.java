package com.github.sunnus3.example.eureka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: zhangwei
 * @date: 14:31/2019-02-17
 */
public interface ExampleApi {

    @GetMapping("/hello/{name}")
    String sayHello(@PathVariable("name") String name);

    @GetMapping("/random/exception")
    String randomException();
}
