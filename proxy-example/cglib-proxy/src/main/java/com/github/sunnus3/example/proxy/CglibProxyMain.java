package com.github.sunnus3.example.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author: zhangwei
 * @date: 15:56/2019-01-23
 */
public class CglibProxyMain {

    public static void main(String[] args) {
        Hello hello = getProxy(Hello.class);
        System.out.println(hello.sayHello("CglibProxyMain"));
        hello = getProxy(HelloImpl.class);
        System.out.println(hello.sayHello("CglibProxyMain"));
    }

    private static Hello getProxy(Class<? extends Hello> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CglibDynamicProxy());
        return (Hello) enhancer.create();
    }
}
