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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author: zhangwei
 * @date: 2019-06-16/12:29
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class ConcurrentTest {

    private static final int loop = 3;


    @Benchmark
    public void checkFinishConditionsOfSynchronized() throws InterruptedException {
        TracingContext context = new TracingContext(false);
        submit(TracingContext::checkFinishConditionsOfSynchronized, context);
    }

    @Benchmark
    public void checkFinishConditionsOfLock() throws InterruptedException {
        TracingContext context = new TracingContext(new ReentrantLock(), false);
        submit(TracingContext::checkFinishConditionsOfLock, context);
    }


    @Benchmark
    public void checkFinishConditionsAsyncOfSynchronized() throws InterruptedException {
        TracingContext context = new TracingContext(true);
        submit(TracingContext::checkFinishConditionsOfSynchronized, context);
    }

    @Benchmark
    public void checkFinishConditionsAsyncOfLock() throws InterruptedException {
        TracingContext context = new TracingContext(new ReentrantLock(), true);
        submit(TracingContext::checkFinishConditionsOfLock, context);
    }


    private void submit(Consumer<TracingContext> consumer, TracingContext context) throws InterruptedException {
        ExecutorService pool = new ThreadPoolExecutor(loop, loop, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++) {
            pool.execute(() -> {
                consumer.accept(context);
                latch.countDown();
            });
        }
        latch.await();
        pool.shutdownNow();
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(ConcurrentTest.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
