package net.abc.example.sentinel.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author: zhangwei
 * @date: 19:30/2019-01-19
 */
public final class ExceptionUtil {

    public static void handlerException(BlockException ex){
        System.out.println("Oops: "+ex.getClass().getCanonicalName());
    }
}
