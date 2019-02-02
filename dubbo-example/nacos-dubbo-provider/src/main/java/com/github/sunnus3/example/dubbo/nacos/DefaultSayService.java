package com.github.sunnus3.example.dubbo.nacos;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.sunnus3.example.dubbo.api.SayService;

/**
 * @author: zhangwei
 * @date: 22:42/2019-02-01
 */
@Service(version = "${nacos.dubbo.version}")
public class DefaultSayService implements SayService {

//    @NacosValue(value = "${preffix:haha}", autoRefreshed = true)
    private String preffix = "haha";

    @Override
    public String say(String name) {
        return String.format("[%s]:%s",preffix, name);
    }
}
