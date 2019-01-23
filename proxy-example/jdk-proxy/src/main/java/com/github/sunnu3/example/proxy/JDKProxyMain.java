package com.github.sunnu3.example.proxy;

import com.github.sunnus3.example.proxy.Hello;
import com.github.sunnus3.example.proxy.HelloImpl;

import java.lang.reflect.Proxy;

/**
 * @author: zhangwei
 * @date: 15:38/2019-01-23
 */
public class JDKProxyMain {

    public static void main(String[] args) {
        JDKDynamicProxy proxy = new JDKDynamicProxy(new HelloImpl());
        // 加上这句会产生一个 $Proxy0.class 文件，这个文件即为动态生成的代理类文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Hello hello = (Hello) Proxy.newProxyInstance(JDKProxyMain.class.getClassLoader(), new Class[]{Hello.class}, proxy);
        System.out.println(hello.sayHello("JDKProxyMain"));
    }

}
