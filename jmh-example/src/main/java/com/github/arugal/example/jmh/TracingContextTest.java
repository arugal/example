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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: zhangwei
 * @date: 2019-06-16/11:29
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class TracingContextTest {


    /**
     * 使用asyncFinishLock时在创建
     */
    @Benchmark
    public void notContainsLock() {
        new TracingContext();
    }

    /**
     * 在构造函数中创建asyncFinishLock,但可能不会使用到
     */
    @Benchmark
    public void containsLock() {
        new TracingContext(new ReentrantLock());
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(TracingContextTest.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
