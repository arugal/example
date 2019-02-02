package com.github.sunnus3.example.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhangwei
 * @date: 17:07/2019-01-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 启动tomcat
public class FooControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private User user = new User(1, "zw");

    @LocalServerPort
    private int port;

    @Test
    public void testFoo(){
        Assert.assertEquals(this.testRestTemplate.getForObject("http://localhost:"+port+"/foo/"+user.getId()+"?name="+user.getName(), User.class),
                user);
    }
}
