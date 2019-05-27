package com.github.arugal.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * @author: zhangwei
 * @date: 2019-05-27/12:58
 */
@SpringBootApplication
public class H2Application implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    public static void main(String[] args) {
        SpringApplication.run(H2Application.class, args);
    }


    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addBuilderCustomizers(builder -> {
            builder.addHttpListener(8080, "0.0.0.0");
        });
    }
}
