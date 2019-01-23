package com.github.sunnus3.example.spring.importbeandefinitionregistrar;

import com.github.sunnus3.example.spring.AbstractApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: zhangwei
 * @date: 15:56/2019-01-22
 */
@SpringBootApplication
@Configuration
@Import(value = {UserServiceImportBeanDefinitionRegistrat.class})
public class ImportBeanDefinitionRegistratApplication extends AbstractApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportBeanDefinitionRegistratApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        super.run(args);
        String[] names = applicationContext.getBeanDefinitionNames();
        for(String name : names){
            System.out.println(name);
        }
    }
}
