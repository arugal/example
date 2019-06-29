package com.github.arugal.example.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangwei
 * @date: 2019-06-29/21:31
 */
@RestController
@RequestMapping("/docker")
public class TestController {


    @GetMapping("/healthy-check")
    public String healthy(){
        return "healthy";
    }
}
