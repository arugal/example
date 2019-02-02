package com.github.sunnus3.example.spring.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 *
 * 假设数据源的健康检查
 *
 * @author: zhangwei
 * @date: 14:38/2019-02-01
 */
@Component("MyHealth")
public class MyHealthIndicator extends AbstractHealthIndicator {

    private static final String VERSION = "v1.0.0";

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int code = check();
        if(code != 0){
            builder.down().withDetail("code", code).withDetail("version", VERSION).build();
        }else{
            builder.up().withDetail("code", code).withDetail("version", VERSION).build();
        }
    }

    private int check(){
        return 0;
    }
}
