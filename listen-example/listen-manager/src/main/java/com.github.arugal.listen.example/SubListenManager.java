package com.github.arugal.listen.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author: zhangwei
 * @date: 2019-06-26/10:46
 */
public class SubListenManager implements ListenManager<SubListenManager.SubContext> {

    @Getter
    private final List<Listen<SubListenManager.SubContext>> listens = new LinkedList<>();

    private final BiConsumer<List<Listen<SubContext>>, SubContext> consumer;

    public SubListenManager(BiConsumer<List<Listen<SubContext>>, SubContext> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void refreshListen(List<Listen> listens) {
        Class scope = listenScope();
        for(Listen listen : listens) {
            final Type[] generics = listen.getClass().getGenericInterfaces();
            for(Type generic : generics) {
                ParameterizedType anInterface = (ParameterizedType) generic;
                if(anInterface.getRawType().getTypeName().equals(Listen.class.getName())) {
                    Type arg = anInterface.getActualTypeArguments()[0];
                    if(arg.equals(scope)) {
                        this.listens.add(listen);
                    }
                }
            }
        }
    }

    @Override
    public void addListen(Listen<SubContext> listen) {
        if(!listens.contains(listen)) {
            listens.add(listen);
        }
    }

    @Override
    public boolean remove(Listen<SubContext> listen) {
        return listens.remove(listen);
    }

    @Override
    public Class listenScope() {
        final Type[] generaics = getClass().getGenericInterfaces();
        for(Type generaic : generaics) {
            ParameterizedType anInterface = (ParameterizedType) generaic;
            if(anInterface.getRawType().getTypeName().equals(ListenManager.class.getName())) {
                return (Class) anInterface.getActualTypeArguments()[0];
            }
        }
        throw new NullPointerException("Scope Miss");
    }

    public void accept(SubContext context) {
        consumer.accept(listens, context);
    }


    @Builder
    public static class SubContext {

        @Setter
        @Getter
        private boolean bit;

        public SubContext() {

        }
    }

    public enum ListenOrderEnum {
        HIGH(1),
        LOW(10);

        private int order;

        ListenOrderEnum(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }


}
