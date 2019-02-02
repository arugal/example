package com.github.sunnus3.example.spring.expand;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author: zhangwei
 * @date: 23:23/2019-01-24
 */
public class ExpandAfterReturningAdvice implements AfterReturningAdvice {


    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("ExpandAfterReturningAdvice");
    }
}
