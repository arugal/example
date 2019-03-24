package com.github.sunnus3.example.proxy;

/**
 * @author: zhangwei
 * @date: 15:30/2019-01-23
 */
public class HelloImpl implements Hello {

    @Override
    public String sayHello(String name) {
        printHello();
        return "say Hello "+name;
    }

    public void printHello(){
        System.out.println("hello word!");
    }
}
