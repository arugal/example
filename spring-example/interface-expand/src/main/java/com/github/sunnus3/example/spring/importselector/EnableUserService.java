package com.github.sunnus3.example.spring.importselector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: zhangwei
 * @date: 16:07/2019-01-22
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(value = {UserServiceImportSelect.class})
public @interface EnableUserService {

    String name();
}
