package com.github.arugal.flink.example;

import com.github.arugal.flink.example.utils.ThrottledIterator;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Iterator;
import java.util.Random;

/**
 * @author zhangwei
 */
public class WindowJoinSampleData {

    static final String[] NAMES = {"tom", "jerry", "alice", "bob", "john", "grace"};
    static final int GRADE_COUNT = 5;
    static final int SALARY_MAX = 10000;

    public static class GradeClass implements Iterator<Tuple2<String, Integer>>, java.io.Serializable {

        private static final long serialVersionUID = 535092985267831985L;

        private final Random rnd = new Random(hashCode());


        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Tuple2<String, Integer> next() {
            return new Tuple2<>(NAMES[rnd.nextInt(NAMES.length)], rnd.nextInt(GRADE_COUNT) + 1);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public static DataStream<Tuple2<String, Integer>> getSource(StreamExecutionEnvironment env, long rate) {
            return env.fromCollection(new ThrottledIterator<>(new GradeClass(), rate),
                    TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {
                    }));
        }
    }

    public static class SalarySource implements Iterator<Tuple2<String, Integer>>, java.io.Serializable {

        private static final long serialVersionUID = -3851740722908248190L;

        private final Random rnd = new Random(hashCode());

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Tuple2<String, Integer> next() {
            return new Tuple2<>(NAMES[rnd.nextInt(NAMES.length)], rnd.nextInt(SALARY_MAX) + 1);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public static DataStream<Tuple2<String, Integer>> getSource(StreamExecutionEnvironment env, long rate) {
            return env.fromCollection(new ThrottledIterator<>(new SalarySource(), rate),
                    TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {}));
        }
    }


}
