package com.github.sunnus3.example.dubbo;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author: zhangwei
 * @date: 23:59/2019-02-02
 */
@SpringBootApplication
@NacosPropertySource(dataId = "provider", groupId = "nacos.dubbo", autoRefreshed = true)
public class NacosProviderApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(NacosProviderApplication.class)
                .run(args);
    }
}
