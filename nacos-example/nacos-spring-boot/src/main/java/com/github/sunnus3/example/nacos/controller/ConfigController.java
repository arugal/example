package com.github.sunnus3.example.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangwei
 * @date: 23:40/2019-01-31
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @NacosValue(value = "${welcome:no}", autoRefreshed = true)
    private String welcome;


    @GetMapping("/get")
    @ResponseBody
    public boolean get(){
        return useLocalCache;
    }

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome(){
        return welcome;
    }
}
