package com.github.arugal.example.jmh;

import io.netty.util.Recycler;
import lombok.Getter;
import lombok.Setter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
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
 * @date: 2019-05-28/23:43
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Threads(4)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class ObjectPoolTest {

    private static Recycler<Span> recycler = new Recycler<Span>() {
        @Override
        protected Span newObject(Handle<Span> handle) {
            return new Span(handle);
        }
    };

    private final String ID = System.currentTimeMillis() + "";

    @Benchmark
    public void testDirectly() {
        for (int i = 0; i < 4000; i++) {
            new Span().setId(ID);
        }
    }

    @Benchmark
    public void testPool() {
        for (int i = 0; i < 4000; i++) {
            Span span = recycler.get();
            span.setId(ID);
            span.recycler();
        }

    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .result("ObjectPool.json")
            .resultFormat(ResultFormatType.TEXT)
            .include(ObjectPoolTest.class.getSimpleName())
            .addProfiler(GCProfiler.class)
            .jvmArgsAppend("-Xmx5M", "-Xms5M")
            .build();
        new Runner(options).run();
    }

    public static class Span {

        @Setter
        @Getter
        private String id;

        private Recycler.Handle<Span> handle;

        public Span(Recycler.Handle<Span> handle) {
            this.handle = handle;
        }

        public Span() {

        }

        public void recycler() {
            id = null;
            if (handle != null)
                handle.recycle(this);
        }
    }

}
