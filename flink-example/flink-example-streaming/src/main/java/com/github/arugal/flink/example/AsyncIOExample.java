package com.github.arugal.flink.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.async.AsyncFunction;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.ExecutorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangwei
 */
public class AsyncIOExample {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncIOExample.class);

    private static final String EXACTLY_ONCE_MODE = "exactly_once";
    private static final String EVENT_TIME = "EventTime";
    private static final String INGESTION_TIME = "IngestionTime";
    private static final String ORDERED = "ordered";

    private static class SimpleSource implements SourceFunction<Integer>, ListCheckpointed<Integer> {

        private static final long serialVersionUID = -7683463558416641378L;

        private volatile boolean isRunning = true;
        private int counter = 0;
        private int start = 0;

        public SimpleSource(int counter) {
            this.counter = counter;
        }

        @Override
        public List<Integer> snapshotState(long l, long l1) throws Exception {
            return Collections.singletonList(start);
        }

        @Override
        public void restoreState(List<Integer> list) throws Exception {
            list.forEach(i -> {
                this.start = i;
            });
        }

        @Override
        public void run(SourceContext<Integer> ctx) throws Exception {
            while ((start < counter || counter == -1) && isRunning) {
                synchronized (ctx.getCheckpointLock()) {
                    ctx.collect(start);
                    ++start;

                    if (start == Integer.MAX_VALUE) {
                        start = 0;
                    }
                    Thread.sleep(10L);
                }
            }
        }

        @Override
        public void cancel() {
            this.isRunning = false;
        }
    }

    private static class SampleAsnycFunction extends RichAsyncFunction<Integer, String> {

        private static final long serialVersionUID = -871835786753194621L;

        private transient ExecutorService executorService;

        private final long sleepFactor;

        private final float failRatio;

        private final long shutdownWaitTS;

        public SampleAsnycFunction(long sleepFactor, float failRatio, long shutdownWaitTS) {
            this.sleepFactor = sleepFactor;
            this.failRatio = failRatio;
            this.shutdownWaitTS = shutdownWaitTS;
        }

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            this.executorService = Executors.newFixedThreadPool(30);
        }

        @Override
        public void close() throws Exception {
            super.close();
            ExecutorUtils.gracefulShutdown(shutdownWaitTS, TimeUnit.MILLISECONDS, executorService);
        }


        @Override
        public void asyncInvoke(Integer input, ResultFuture<String> resultFuture) throws Exception {
            executorService.submit(() -> {
                long sleep = (long) (ThreadLocalRandom.current().nextFloat() * sleepFactor);
                try {
                    Thread.sleep(sleep);

                    if (ThreadLocalRandom.current().nextFloat() < failRatio) {
                        resultFuture.completeExceptionally(new Exception("hahahah..."));
                    } else {
                        resultFuture.complete(Collections.singletonList("key-" + (input % 10)));
                    }
                } catch (InterruptedException e) {
                    resultFuture.complete(Collections.emptyList());
                }
            });
        }
    }

    private static void printUsage() {
        System.out.println("To customize example, use: AsyncIOExample [--fsStatePath <path to fs state>] " +
                "[--checkpointMode <exactly_once or at_least_once>] " +
                "[--maxCount <max number of input from source, -1 for infinite input>] " +
                "[--sleepFactor <interval to sleep for each stream element>] [--failRatio <possibility to throw exception>] " +
                "[--waitMode <ordered or unordered>] [--waitOperatorParallelism <parallelism for async wait operator>] " +
                "[--eventType <EventTime or IngestionTime>] [--shutdownWaitTS <milli sec to wait for thread pool>]" +
                "[--timeout <Timeout for the asynchronous operations>]");
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        final ParameterTool params = ParameterTool.fromArgs(args);

        final String statePath;
        final String cpMode;
        final int maxCount;
        final long sleepFactor;
        final float failRatio;
        final String mode;
        final int taskNum;
        final String timeType;
        final long shutdownWaitTS;
        final long timeout;

        try {
            statePath = params.get("fsStatePath", null);
            cpMode = params.get("checkpointMode", "exactly_once");
            maxCount = params.getInt("maxCount", 100000);
            sleepFactor = params.getLong("sleepFactor", 100);
            failRatio = params.getFloat("failRatio", 0.001f);
            mode = params.get("waitMode", "ordered");
            taskNum = params.getInt("waitOperatorParallelism", 1);
            timeType = params.get("eventType", "EventTime");
            shutdownWaitTS = params.getLong("shutdownWaitTS", 20000);
            timeout = params.getLong("timeout", 10000L);
        } catch (Exception e) {
            printUsage();
            throw e;
        }

        StringBuffer configStringBuilder = new StringBuffer();

        final String lineSeparator = System.getProperty("line.separator");

        configStringBuilder
                .append("Job configuration").append(lineSeparator)
                .append("FS state path=").append(statePath).append(lineSeparator)
                .append("Checkpoint mode=").append(cpMode).append(lineSeparator)
                .append("Max count of input from source=").append(maxCount).append(lineSeparator)
                .append("Sleep factor=").append(sleepFactor).append(lineSeparator)
                .append("Fail ratio=").append(failRatio).append(lineSeparator)
                .append("Waiting mode=").append(mode).append(lineSeparator)
                .append("Parallelism for async wait operator=").append(taskNum).append(lineSeparator)
                .append("Event type=").append(timeType).append(lineSeparator)
                .append("Shutdown wait timestamp=").append(shutdownWaitTS);

        LOG.info(configStringBuilder.toString());

        if (statePath != null) {
            env.setStateBackend(new FsStateBackend(statePath));
        }

        if (EXACTLY_ONCE_MODE.equals(cpMode)) {
            env.enableCheckpointing(1000L, CheckpointingMode.EXACTLY_ONCE);
        } else {
            env.enableCheckpointing(1000L, CheckpointingMode.AT_LEAST_ONCE);
        }

        if (EVENT_TIME.equals(timeType)) {
            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        } else if (INGESTION_TIME.equals(timeType)) {
            env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        }

        DataStream<Integer> inputStream = env.addSource(new SimpleSource(maxCount));

        AsyncFunction<Integer, String> function = new SampleAsnycFunction(sleepFactor, failRatio, shutdownWaitTS);

        DataStream<String> result;
        if (ORDERED.equals(mode)) {
            result = AsyncDataStream.orderedWait(
                    inputStream,
                    function,
                    timeout,
                    TimeUnit.MILLISECONDS,
                    20
            ).setParallelism(taskNum);
        } else {
            result = AsyncDataStream.unorderedWait(
                    inputStream,
                    function,
                    timeout,
                    TimeUnit.MILLISECONDS,
                    20
            ).setParallelism(taskNum);
        }

        result.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            private static final long serialVersionUID = -938116068682344455L;

            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                out.collect(new Tuple2<>(value, 1));
            }
        }).keyBy(0).sum(1).print();

        env.execute("Async IO Example");
    }
}
