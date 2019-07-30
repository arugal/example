package com.github.arugal.jdk.example;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhangwei
 */
public class StreamTest {

    @Test
    public void streamTest() {
        int maxLength = Arrays.asList("A", "AA").stream()
                .filter(a -> a.startsWith("A"))
                .mapToInt(a -> a.length())
                .max().getAsInt();
        System.out.println(maxLength);
    }

    @Test
    public void findFirst() {
        Integer first = Arrays.asList(1, 2, 3)
                .stream()
                .map(x -> {
                    System.out.println(x);
                    return x;
                })
                .filter(x -> x >= 3)
                .findFirst().get();

        System.out.println("first:" + first);
    }
}
