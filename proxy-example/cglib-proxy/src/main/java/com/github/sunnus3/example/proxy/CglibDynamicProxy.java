package com.github.sunnus3.example.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: zhangwei
 * @date: 15:54/2019-01-23
 */
public class CglibDynamicProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("target class:" + o.getClass().getName() + "/method" + method.getName() + "/methodProxy:" +
                methodProxy.getSuperName());
        Object result = null;
        try {
            result = methodProxy.invokeSuper(o, objects);
        } catch (Throwable e) {
            result = e.getClass().getName();
        }
        if (result instanceof String) {
            String str = (String) result;
            result = str + " after return";
        }
        return result;
    }
}
