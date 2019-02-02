package com.github.sunnus3.example.nacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 23:58/2019-01-31
 */
@RestController
@RequestMapping("/discovery")
public class NamingController {

    @NacosInjected
    private NamingService namingService;

    @GetMapping("/get/{serviceName}")
    public List<Instance> get(@PathVariable String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }
}
