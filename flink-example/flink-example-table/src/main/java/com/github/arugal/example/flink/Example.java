package com.github.arugal.example.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.util.function.BiConsumer;

/**
 * @author zhangwei
 */
public final class Example {

    private Example() {

    }

    public static void wrapStreamTableEnv(BiConsumer<StreamExecutionEnvironment, StreamTableEnvironment> consumer) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        consumer.accept(env, tEnv);

        env.execute();
    }

}
