package com.github.sunnus3.example.spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangwei
 * @date: 17:06/2019-01-25
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    @RequestMapping("/{id}")
    public User foo(@PathVariable Integer id, @RequestParam String name){
        return new User(id, name);
    }
}
