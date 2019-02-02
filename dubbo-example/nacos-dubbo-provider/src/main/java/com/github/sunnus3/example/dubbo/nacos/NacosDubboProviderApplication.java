package com.github.sunnus3.example.dubbo.nacos;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.github.sunnus3.example.dubbo.api.SayService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * @author: zhangwei
 * @date: 22:43/2019-02-01
 */
@SpringBootApplication
@EnableDubbo
public class NacosDubboProviderApplication {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(NacosDubboProviderApplication.class)
                .run(args);

        String[] beans = context.getBeanNamesForType(SayService.class);
        for(String bean : beans){
            System.out.println(bean);
        }
    }
}
