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
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 2019-06-06/10:22
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class StringJoinTest {

    @Benchmark
    public void testJoinDirect() {
        String str = "a" + "b" + "c";
    }

    @Benchmark
    public void testJoinBuilder() {
        new StringBuilder().append("a").append("b").append("c").toString();
    }

    @Benchmark
    public void testJoinConcat() {
        "a".concat("b").concat("c");
    }


    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StringJoinTest.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .jvmArgsAppend("-Xms10M", "-Xmx10M")
                .build();
        new Runner(options).run();
    }
}
