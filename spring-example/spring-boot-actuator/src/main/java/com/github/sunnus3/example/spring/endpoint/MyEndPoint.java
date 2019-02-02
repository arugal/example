package com.github.sunnus3.example.spring.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 *
 * actuator 从 Bean 的基础上解析 Endpoint 注解，所以需要现将 Endpoint 注册成 Bean
 *
 * {@link Endpoint} 构建 rest 的唯一路径
 * {@link ReadOperation} GET 响应状态为 200, 如果没有返回值响应 404 (资源未找到)
 * {@link org.springframework.boot.actuate.endpoint.annotation.WriteOperation} POST 响应状态为 200, 如果没有返回值响应 204 (无响应内容)
 * {@link org.springframework.boot.actuate.endpoint.annotation.DeleteOperation} DELETE 响应状态为 200, 如果没有返回值响应 204 (无响应内容)
 * @author: zhangwei
 * @date: 14:51/2019-02-01
 */
@Endpoint(id = "battcn")
public class MyEndPoint {

    @ReadOperation
    public Info hello(){
       return new Info();
    }

}
