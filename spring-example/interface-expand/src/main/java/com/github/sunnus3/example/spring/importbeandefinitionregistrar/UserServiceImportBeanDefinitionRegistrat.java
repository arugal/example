package com.github.sunnus3.example.spring.importbeandefinitionregistrar;

import com.github.sunnus3.example.spring.UserServiceImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: zhangwei
 * @date: 15:56/2019-01-22
 */
public class UserServiceImportBeanDefinitionRegistrat implements ImportBeanDefinitionRegistrar {

    public UserServiceImportBeanDefinitionRegistrat(){
        System.out.println();
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder userService = BeanDefinitionBuilder.rootBeanDefinition(UserServiceImpl.class);
        registry.registerBeanDefinition("userService", userService.getBeanDefinition());
    }
}
