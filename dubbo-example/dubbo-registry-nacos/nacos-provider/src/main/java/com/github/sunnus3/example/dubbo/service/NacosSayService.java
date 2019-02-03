package com.github.sunnus3.example.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.github.sunnus3.example.dubbo.SayService;

/**
 * @author: zhangwei
 * @date: 23:59/2019-02-02
 */
@Service(version = "1.0.0")
public class NacosSayService implements SayService {

    @NacosValue(value = "${preffix:nacos}", autoRefreshed = true)
    private String preffix;

    @Override
    public String say(String name) {
        return String.format("[%s]:%s", preffix, name);
    }
}
