package com.github.sunnus3.example.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 14:21/2019-01-23
 */
@Aspect
@Component
public class AopAspect {

    public AopAspect(){
        System.out.println();
    }

    @Pointcut("execution(* com.github.sunnus3.example.spring.UserServiceImpl.save(User))")
    public void aop(){}

    @Before("aop()")
    public void before(JoinPoint joinPoint){
        System.out.println(joinPoint.toString());
        System.out.println(joinPoint.toShortString());
        System.out.println(joinPoint.toLongString());
        System.out.println(joinPoint.getThis().getClass().getName());
        System.out.println(joinPoint.getTarget().getClass().getName());
        System.out.println(joinPoint.getArgs());
        System.out.println(joinPoint.getSignature().getName());
        System.out.println(joinPoint.getKind());
        System.out.println("save aop before");
    }

    @AfterReturning(pointcut = "aop()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        System.out.println("save aop afterReturning");
    }

    @After("aop()")
    public void after(JoinPoint joinPoint){
        System.out.println("save aop after");
    }

//    @AfterThrowing(pointcut = "aop()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex){
        System.out.println("save aop afterThrowing");
    }
}

