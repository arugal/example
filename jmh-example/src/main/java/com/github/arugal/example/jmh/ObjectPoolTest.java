package com.github.arugal.example.jmh;

import io.netty.util.Recycler;
import lombok.Data;
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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: zhangwei
 * @date: 2019-05-28/23:43
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class ObjectPoolTest {

    private static Recycler<Span> recycler = new Recycler<Span>() {

        @Override
        protected Span newObject(Handle<Span> handle) {
            return new Span(handle);
        }
    };

    private static final int THREAD_NUM = 100;

    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

    private static final int LOOP = 1000;

    private static final String ID = System.currentTimeMillis()+"";

    public void process(Consumer<Integer> consumer) {
        Long startTime = System.currentTimeMillis();
        List<Future> futures = new ArrayList<>();
        for(int i = 0; i < THREAD_NUM; i++) {
            Future future = executor.submit(() -> {
                for(int j = 0; j < LOOP; j++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                    consumer.accept(j);
                }
            });
            futures.add(future);
        }

        futures.forEach((f) -> {
            try {
                f.get();
            } catch (Exception e) {
                // ignore
            }
        });
        System.out.println("process " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Benchmark
    public void testNew() {
        process((Integer) -> {
            new Span().id = ID;

        });
    }

    @Benchmark
    public void testPool() {
        process((Integer) -> {
            Span span = recycler.get();
            span.id = ID;
            span.recycler();
        });
    }

    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public static void main(String[] args) throws RunnerException {
//        Options options = new OptionsBuilder().include(ObjectPoolTest.class.getSimpleName()).build();
//        new Runner(options).run();
        ObjectPoolTest test = new ObjectPoolTest();

        test.testNew();
        test.testPool();
        test.testNew();
        test.testPool();

        executor.shutdown();
    }

    @Data
    public static class Span {

        private String id;

        private Recycler.Handle<Span> handle;

        public Span(Recycler.Handle<Span> handle) {
            this.handle = handle;
        }

        public Span() {

        }

        public void recycler() {
            id = null;
            if(handle != null)
                handle.recycle(this);
        }
    }

}
