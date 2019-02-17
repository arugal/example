package com.github.sunnus3.example.sentinel;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.github.sunnus3.example.sentinel.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 19:20/2019-01-19
 */
@SpringBootApplication
public class SentinelApplication implements CommandLineRunner {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("hello");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        for(int i = 0; i < 10; i++){
            testService.hello(System.currentTimeMillis());
        }
    }
}
