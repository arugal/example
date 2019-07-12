package com.github.sunnus3.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
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

    public FooController(){
        System.out.println();
    }

    @Autowired
    private AopAspect aopAspect;

    @RequestMapping("/foo/{id}")
    private User foo(@PathVariable Integer id, @RequestParam String name){
        System.out.println(aopAspect == null);
        System.out.println(System.identityHashCode(this));
        return new User(id, name);
    }

    @RequestMapping("/foo1/{id}")
    public User foo1(@PathVariable Integer id, @RequestParam String name){
        System.out.println(aopAspect == null);
        System.out.println(this.toString());
        System.out.println(System.identityHashCode(this));
        return new User(id, name);
    }

    @Override
    public String toString() {
        return "real";
    }
}
