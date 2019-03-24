package com.github.arugal.example.agent.simple;

import java.lang.instrument.Instrumentation;

/**
 * @author: zhangwei
 * @date: 10:52/2019-03-24
 */
public class SimpleAgent {


    public static void premain(String agentOps, Instrumentation inst){
        System.out.println("invoke premain[String,Instrumentation]:"+agentOps);
    }

    public static void premain(String agentOps){
        System.out.println("invoker premain[String]:"+agentOps);
    }

}
