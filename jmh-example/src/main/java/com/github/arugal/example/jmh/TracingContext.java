package com.github.arugal.example.jmh;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * @author: zhangwei
 * @date: 2019-06-16/12:27
 */
@Data
public class TracingContext {

    private Lock asyncFinishLock;

    private AtomicInteger asyncSpanCounter;

    private volatile boolean isRunningInAsyncMode;

    public TracingContext(Lock lock) {
        this.isRunningInAsyncMode = false;
        this.asyncFinishLock = lock;
        this.asyncSpanCounter = new AtomicInteger(0);
    }

    public TracingContext() {
        this.isRunningInAsyncMode = false;
        this.asyncSpanCounter = new AtomicInteger(0);
    }

    public TracingContext(boolean isRunningInAsyncMode) {
        this.isRunningInAsyncMode = isRunningInAsyncMode;
        this.asyncSpanCounter = new AtomicInteger(0);
    }

    public TracingContext(Lock lock, boolean isRunningInAsyncMode) {
        this.isRunningInAsyncMode = isRunningInAsyncMode;
        this.asyncFinishLock = lock;
        this.asyncSpanCounter = new AtomicInteger(0);
    }

    public void checkFinishConditionsOfLock() {
        if(isRunningInAsyncMode) {
            asyncFinishLock.lock();
        }
        try {
            doSomeThing();
        } finally {
            if(isRunningInAsyncMode) {
                asyncFinishLock.unlock();
            }
        }
    }

    public void checkFinishConditionsOfSynchronized() {
        if(isRunningInAsyncMode) {
            synchronized (this) {
                doSomeThing();
            }
        } else {
            doSomeThing();
        }
    }

    private void doSomeThing() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
