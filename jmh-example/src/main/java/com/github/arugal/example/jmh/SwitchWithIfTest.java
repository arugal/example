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

/**
 * @author: zhangwei
 * @date: 2019-06-22/22:56
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class SwitchWithIfTest {

    private static String condition = "A";


    @Benchmark
    public void testIf() {
        if("B".equals(condition)) {
            // ignore
        } else if("C".equals(condition)) {
            // ignore
        } else if("D".equals(condition)) {
            // ignore
        } else if("E".equals(condition)) {
            // ignore
        } else if("F".equals(condition)) {
            // ignore
        } else {

        }
    }

    @Benchmark
    public void testSwitch() {
        switch(condition) {
            case "B":
                // ignore
                break;
            case "C":
                // ignore
                break;
            case "D":
                // ignore
                break;
            case "E":
                // ignore
                break;
            case "F":
                // ignore
                break;
            default:
                // ignore
                break;
        }
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(SwitchWithIfTest.class.getSimpleName()).build();
        new Runner(options).run();
    }

}
