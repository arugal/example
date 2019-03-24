package com.github.sunnus3.example.spring.expand;

import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhangwei
 * @date: 23:26/2019-01-24
 */
@Configuration
public class ExpandConfiguration {

//    @Bean
    public RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor(){
        RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor = new RegexpMethodPointcutAdvisor();
        regexpMethodPointcutAdvisor.setAdvice(new ExpandAfterReturningAdvice());
        regexpMethodPointcutAdvisor.setPattern("com\\.github\\.sunnus3\\.example\\.spring\\.UserServiceImpl\\.save");
        return regexpMethodPointcutAdvisor;
    }
}
