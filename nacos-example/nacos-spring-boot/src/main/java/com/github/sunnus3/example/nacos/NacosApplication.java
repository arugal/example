package com.github.sunnus3.example.nacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangwei
 * @date: 23:35/2019-01-31
 */
@SpringBootApplication
@NacosPropertySource(dataId = "nacos-spring-boot", groupId = "nacos-example", autoRefreshed = true)
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }
}
