package com.github.arugal.listen.example;

/**
 * @author: zhangwei
 * @date: 2019-06-26/10:47
 */
@FunctionalInterface
public interface Listen<CONTEXT> {

    default int order() {
        return SubListenManager.ListenOrderEnum.LOW.getOrder();
    }

    void notify(CONTEXT context);
}
