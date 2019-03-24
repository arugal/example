package com.github.arugal.example.agent.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author: zhangwei
 * @date: 11:19/2019-03-24
 */
public class ByteBuddyAgent {


    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("invoke byte buddy agent[String, Instrumentation]:" + agentArgs);

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                // 拦截任意方法
                return builder.method(ElementMatchers.<MethodDescription>any())
                        // 委托
                        .intercept(MethodDelegation.to(TimeInterceptor.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener.Adapter() {
            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                super.onComplete(typeName, classLoader, module, loaded);
            }
        };

        new AgentBuilder.Default()
                .type(ElementMatchers.nameEndsWith("Timed"))
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }
}
