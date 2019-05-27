package com.github.arugal.example.spring;

import com.github.sunnus3.example.spring.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangwei
 * @date: 2019-05-27/12:59
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping("/{id}")
    public User foo(@PathVariable Integer id, @RequestParam String name){
        return new User(id, name);
    }
}
