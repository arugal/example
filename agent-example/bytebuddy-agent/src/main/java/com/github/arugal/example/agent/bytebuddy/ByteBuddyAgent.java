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

                StringBuilder stackMessage = new StringBuilder();

                printStackElement(Thread.currentThread().getStackTrace(), new AppendListener() {
                    @Override
                    public void append(String value) {
                        stackMessage.append(value);
                    }

                    @Override
                    public boolean overMaxLength() {
                        return false;
                    }
                });
                System.out.println(stackMessage.toString());
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

    private static String convert2String(Throwable throwable, final int maxLength) {
        final StringBuilder stackMessage = new StringBuilder();
        Throwable causeException = throwable;
        while(causeException != null) {
            stackMessage.append(printExceptionInfo(causeException));

            boolean overMaxLength = printStackElement(throwable.getStackTrace(), new AppendListener() {
                public void append(String value) {
                    stackMessage.append(value);
                }

                public boolean overMaxLength() {
                    return stackMessage.length() > maxLength;
                }
            });

            if(overMaxLength) {
                break;
            }

            causeException = throwable.getCause();
        }

        return stackMessage.toString();
    }

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static String printExceptionInfo(Throwable causeException) {
        return causeException.toString() + LINE_SEPARATOR;
    }

    private static boolean printStackElement(StackTraceElement[] stackTrace, AppendListener printListener) {
        for(StackTraceElement traceElement : stackTrace) {
            printListener.append("at " + traceElement + LINE_SEPARATOR);
            if(printListener.overMaxLength()) {
                return true;
            }
        }
        return false;
    }

    private interface AppendListener {
        void append(String value);

        boolean overMaxLength();
    }

}
