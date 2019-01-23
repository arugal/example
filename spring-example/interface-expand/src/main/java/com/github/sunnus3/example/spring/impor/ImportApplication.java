package com.github.sunnus3.example.spring.impor;

import com.github.sunnus3.example.spring.AbstractApplication;
import com.github.sunnus3.example.spring.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author: zhangwei
 * @date: 15:14/2019-01-22
 */
@SpringBootApplication
@Import(value = {UserServiceImpl.class})
public class ImportApplication extends AbstractApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportApplication.class, args);
    }

}
