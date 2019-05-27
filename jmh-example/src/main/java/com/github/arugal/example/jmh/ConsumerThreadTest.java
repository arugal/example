package com.github.arugal.example.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class ConsumerThreadTest {
    private LinkedList<Object> container = new LinkedList<Object>();
    private static final Object o = new Object();
    private static final List list = new ArrayList();

    static {
        for (int i = 0; i < 100; i++) {
            list.add(0);
        }
    }

    @Benchmark
    public void testAdd() {
        for (int i = 0; i < 100; i++) {
            container.add(o);
        }
        container.clear();
    }

    @Benchmark
    public void testAddAll(){
        container.addAll(list);
        container.clear();
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(ConsumerThreadTest.class.getSimpleName()).build();
        new Runner(options).run();
    }
}