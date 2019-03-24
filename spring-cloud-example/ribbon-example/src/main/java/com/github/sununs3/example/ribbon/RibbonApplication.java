package com.github.sununs3.example.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author: zhangwei
 * @date: 22:31/2019-02-16
 */
@SpringBootApplication
public class RibbonApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RibbonApplication.class)
                .run(args);
    }

    @Bean
    public ApplicationRunner runner(){
        return args -> {
            ConfigurationManager.loadCascadedPropertiesFromResources("ribbon-client");
            ILoadBalancer loadBalancer = ClientFactory.getNamedLoadBalancer("RibbonClient");
            IRule chooseRule = new RandomRule();
            chooseRule.setLoadBalancer(loadBalancer);
            for(int i = 0; i < 10; i++){
                Server server = chooseRule.choose(null);
                System.out.println("server:"+server.getHostPort());
            }
        };
    }
}
