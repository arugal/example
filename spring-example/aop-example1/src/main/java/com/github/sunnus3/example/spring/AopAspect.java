package com.github.sunnus3.example.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 2019-07-12/13:53
 */
@Aspect
@Component
public class AopAspect {

    @Pointcut("execution(* com.github.sunnus3.example.spring.Service.*(..))")
    public void aop() {

    }

    @Before("aop()")
    public void before(JoinPoint joinPoint) {
        System.out.println("before ...");
    }

}
