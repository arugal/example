package com.github.sunnus3.example.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.sunnus3.example.dubbo.SayService;

/**
 * @author: zhangwei
 * @date: 23:37/2019-02-02
 */
@Service(version = "${auto.dubbo.version}")
public class AutoSayService implements SayService {

    private String preffix = "auto";

    @Override
    public String say(String name) {
        return String.format("[%s]:%s", preffix, name);
    }
}
