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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 2019-06-24/13:33
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class SimpleDateFormatTest {

    private static ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMM");
        }
    };

    private static String dateStr = "201906";

    @Benchmark
    public void staticTest() {
        try {
            local.get().parse(dateStr).getTime();
        } catch (ParseException e) {
            // ignore
        }
    }

    @Benchmark
    public void newTest() {
        try {
            new SimpleDateFormat("yyyyMM").parse(dateStr).getTime();
        } catch (ParseException e) {
            // ignore
        }
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(SimpleDateFormatTest.class.getSimpleName()).build();
        new Runner(options).run();
    }

}
