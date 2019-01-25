package com.github.sunnus3.example.spring;

import com.github.sunnus3.example.spring.entity.Company;
import com.github.sunnus3.example.spring.repository.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.github.sunnus3.example.spring.util.DBUtil.init;

/**
 * @author: zhangwei
 * @date: 10:21/2019-01-24
 */
@SpringBootApplication
@Configuration
public class MybatisApplication extends AbstractApplication {

    static {
        init();
    }

    @Autowired
    private CompanyService companyService;

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        companyService.test();
    }
}
