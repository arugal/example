package com.github.sunnus3.example.spring.importselector;

import com.github.sunnus3.example.spring.AbstractApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangwei
 * @date: 16:11/2019-01-22
 */
@SpringBootApplication
@EnableUserService(name = "userService")
public class ImportSelectorApplication extends AbstractApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportSelectorApplication.class, args);
    }

}
