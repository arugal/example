package com.github.arugal.example.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 2019-07-05/22:59
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
public class LuaTest {

    private JedisPool jedisPool;

    private String key = "lua";

    @Setup
    public void up() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(20);
        jedisPool = new JedisPool(config, "dell.com", 6379);
    }

    @TearDown
    public void down() {
        if(jedisPool != null) {
            jedisPool.close();
        }
    }

    @Benchmark
    public void redis() {
        Jedis jedis = jedisPool.getResource();
        Long incr = jedis.incr(key);
        if(incr > 0) {
            jedis.incrBy(key, -1);
        }
    }

    @Benchmark
    public void redisLua() {
        Jedis jedis = jedisPool.getResource();
        jedis.eval("local incr = redis.call('INCRBY', KEYS[1], 1)\n" +
                "\n" +
                "local target = tonumber(ARGV[1])\n" +
                "\n" +
                "if incr > target\n" +
                "then\n" +
                "    redis.call('INCRBY', KEYS[1], -1)\n" +
                "end", Collections.singletonList(key), Collections.singletonList("0"));
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .result("result.json")
                .resultFormat(ResultFormatType.JSON)
                .include(LuaTest.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
