package com.github.sunnus3.example.hystrix.order;

import com.netflix.hystrix.*;

import java.util.Random;

/**
 * @author: zhangwei
 * @date: 20:27/2019-02-17
 */
public class GetOrderCircuitBreakerCommand extends HystrixCommand<String> {

    private final Random random = new Random();

    public GetOrderCircuitBreakerCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThreadPoolGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 是否启动熔断器，默认值 true
                        .withCircuitBreakerEnabled(true)
                        // 熔断器强制打开，始终保持打开状态，默认值 false
                        .withCircuitBreakerForceOpen(false)
                        // 熔断器强制关闭，始终保持关闭状态，默认值 false
                        .withCircuitBreakerForceClosed(false)
                        /**
                         * 设定错误百分比，默认值 50%，例如一段时间（10s）内有一百个请求，其中55个超时或者异常返回了，那么这段
                         * 时间内的错误百分比是 55%，大于了默认值 50%，这种情况下熔断器会打开
                         */
                        .withCircuitBreakerErrorThresholdPercentage(5)
                        /**
                         * 默认值 20，意思是至少有20个请求才会进行 ErrorThresholdPercentage 百分比计算，比如
                         * 一段时间内（10s）内有19个请求全部失败，错误百分比是100%，但熔断器不会打开，因为 equestVolumeThreshold
                         * 是 20
                         */
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        /**
                         * 半开试探休眠时间，默认值 5000ms, 当熔断器开启一段时间后比如 5000ms,会尝试过去一部分流量进行试探，
                         * 确定依赖服务是否恢复
                         */
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        // 服务调用超时时间,默认 2s
                        .withExecutionTimeoutInMilliseconds(2000)
                        // 是否启用 timeout 机制，默认 true
                        .withExecutionTimeoutEnabled(true))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        // 队列大小
                        .withMaxQueueSize(10)
                        // 线程池大小
                        .withCoreSize(2)));
    }

    @Override
    protected String run() throws Exception {
        if (1 == random.nextInt(2)) {
            throw new Exception("make exception");
        }
        return "running: ";
    }

    @Override
    protected String getFallback() {
        return "fallback: ";
    }
}
