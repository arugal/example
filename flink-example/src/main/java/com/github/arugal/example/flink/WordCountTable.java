package com.github.arugal.example.flink;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * @author zhangwei
 */
public class WordCountTable {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);

        DataSet<WC> input = env.fromElements(
                new WC("Hello", 1),
                new WC("Ciao", 1),
                new WC("Hello", 1)
        );

        Table table = tEnv.fromDataSet(input);

        Table filtered = table
                .groupBy("word")
                .select("word, frequency.sum as frequency")
                .filter("frequency = 2");

        DataSet<WC> result = tEnv.toDataSet(filtered, WC.class);

        result.print();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class WC {
        public String word;
        public long frequency;

    }
}
