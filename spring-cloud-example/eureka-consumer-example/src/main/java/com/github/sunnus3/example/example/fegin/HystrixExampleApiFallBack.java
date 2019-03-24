package com.github.sunnus3.example.example.fegin;

import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 19:52/2019-02-18
 */
@Component
public class HystrixExampleApiFallBack implements HystrixExampleApiRemote {

    @Override
    public String sayHello(String name) {
        return String.format("say hello fallback by %s", name);
    }

    @Override
    public String randomException() {
        return "fall!";
    }
}
