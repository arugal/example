package com.github.sunnus3.example.sentinel.contorller;

import com.github.sunnus3.example.sentinel.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangwei
 * @date: 19:34/2019-01-19
 */
@RestController
public class SentinelController {

    @Autowired
    private TestService testService;

    @GetMapping("/foo")
    public String foo() throws Exception{
        testService.test();
        return testService.hello(System.currentTimeMillis());
    }
}
