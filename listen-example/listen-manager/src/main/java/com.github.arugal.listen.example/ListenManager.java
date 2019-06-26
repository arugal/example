package com.github.arugal.listen.example;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 2019-06-26/11:07
 */
public interface ListenManager<CONTEXT> {

    void refreshListen(List<Listen> listen);

    List<Listen<CONTEXT>> getListens();

    void addListen(Listen<CONTEXT> listen);

    boolean remove(Listen<CONTEXT> listen);

    Class listenScope();

    void accept(CONTEXT context);
}
