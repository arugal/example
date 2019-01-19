package net.abc.example.swagger;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: zhangwei
 * @date: 19:48/2019-01-19
 */
@SpringBootApplication
@EnableSwagger2Doc
public class SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
