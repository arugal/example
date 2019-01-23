package com.github.sunnu3.example.proxy;

import com.github.sunnus3.example.proxy.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: zhangwei
 * @date: 15:34/2019-01-23
 */
public class JDKDynamicProxy<T extends Hello> implements InvocationHandler {

    private T hello;

    public JDKDynamicProxy(T hello){
        this.hello = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDKDynamicProxy before");
        Object result = method.invoke(hello, args);
        if(result instanceof String){
            String str = (String) result;
            result = str + " after return";
        }
        System.out.println("JDKDynamicProxy after");
        return result;
    }
}
