package com.github.arugal.flink.example.utils;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author zhangwei
 */
public class ThrottledIterator<T> implements Iterator<T>, java.io.Serializable {

    private static final long serialVersionUID = 5519189772736613729L;

    private final Iterator<T> source;

    private final long sleepBatchSize;
    private final long sleepBatchTime;

    private long lastBatchCheckTime;
    private long num;

    public ThrottledIterator(Iterator<T> source, long elementPerSecond) {
        this.source = source;

        if (!(source instanceof Serializable)) {
            throw new IllegalArgumentException("source must be java.io.Serializable");
        }

        if (elementPerSecond >= 100) {
            this.sleepBatchSize = elementPerSecond / 20;
            this.sleepBatchTime = 50;
        } else if (elementPerSecond >= 1) {
            this.sleepBatchSize = 1;
            this.sleepBatchTime = 1000 / elementPerSecond;
        } else {
            throw new IllegalArgumentException("'elements per second' must be positive and not zero");
        }
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        if (lastBatchCheckTime > 0) {
            if (++num >= sleepBatchSize) {
                num = 0;
                final long now = System.currentTimeMillis();
                final long elapsed = now - lastBatchCheckTime;
                if (elapsed < sleepBatchTime) {
                    try {
                        Thread.sleep(sleepBatchTime - elapsed);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                lastBatchCheckTime = now;
            }
        } else {
            lastBatchCheckTime = System.currentTimeMillis();
        }
        return source.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
