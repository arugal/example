package com.github.sunnus3.example.spring;

import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 2019-07-12/13:54
 */
@Component
public class Service {


    private void method1() {
        System.out.println("private method: " + System.identityHashCode(this));
    }

    public void method2() {
        System.out.println("public  method: " + System.identityHashCode(this));
    }
}
