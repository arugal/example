package com.github.arugal.jdk.example;

import org.junit.Test;

/**
 * @author zhangwei
 */

public class AutoCloseableTest {

    @Test
    public void tryTest() {
        try(AutoStream stream = new AutoStream()){

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AutoStream implements AutoCloseable {

        @Override
        public void close() throws Exception {
            System.out.println("auto closeable");
        }
    }
}
