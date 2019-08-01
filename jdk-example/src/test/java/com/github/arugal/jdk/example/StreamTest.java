package com.github.arugal.jdk.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

/**
 * @author zhangwei
 */
public class StreamTest {

    @Test
    public void streamTest() {
        int maxLength = Stream.of("A", "AA")
                .filter(a -> a.startsWith("A"))
                .mapToInt(String::length)
                .max().getAsInt();
        System.out.println(maxLength);
    }

    @Test
    public void findFirstTest() {
        Integer first = Stream.of(1, 2, 3)
                .peek(System.out::println)
                .filter(x -> x >= 3)
                .findFirst().get();

        System.out.println("first:" + first);
    }

    @Test
    public void parallelStreamTest() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        list.parallelStream()
                .filter(x -> x >= 9999)
                .findFirst().get();
    }

    @Test
    public void spliteratorTest() {
        Spliterator<Integer> spliterator = Arrays.asList(1, 2, 3, 4)
                .spliterator();

        spliterator.forEachRemaining(System.out::println);
    }


    public static class ForkJoinSumCalculator extends RecursiveTask<Long> {

        private static final long serialVersionUID = -1332593115901476964L;

        private final long[] numbers;
        private final int start;
        private final int end;

        private static final long THRESHOLD = 10_000;

        public ForkJoinSumCalculator(long[] numbers) {
            this(numbers, 0, numbers.length);
        }

        public ForkJoinSumCalculator(long[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int lenght = end - start;
            if (lenght <= THRESHOLD) {
                return computeSequentially();
            }
            ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + lenght / 2);
            leftTask.fork();

            ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + lenght / 2, end);
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();
            return leftResult + rightResult;
        }

        private long computeSequentially() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }
    }
}
