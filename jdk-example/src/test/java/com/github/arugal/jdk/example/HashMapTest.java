package com.github.arugal.jdk.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangwei
 */
public class HashMapTest {

    @Test
    public void test1() {
        HashMap<Hash, Integer> map = new HashMap<>(4);

        for (int i = 0; i < 10; i++) {
            Hash hash = Hash.builder().key(i).build();
            map.put(hash, i);
        }
    }

    @Test
    public void test2() {
        System.out.println(2 ^ 1);
    }

    @Test
    public void test3() {
        System.out.println(Integer.toBinaryString(15 << 2));
        System.out.println(15 << 2);
    }

    @Test
    public void test4() {
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-2 >> 1) + " " + (-2 >> 1));
        System.out.println(Integer.toBinaryString(-2 >>> 1) + " " + (-2 >>> 1));
    }

    @Test
    public void test5() {
        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("a", "b");
    }

    @Test
    public void test6() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        map.put("1", "1");
    }

    @Test
    public void test7() {
        Long[] abc = {1L, 2L};
        System.out.println(Long[].class.isArray() && long[].class.getComponentType().isPrimitive());
        System.out.println(abc);
    }


    @Setter
    @Getter
    @Builder
    public static class Hash {

        private Integer key;


        @Override
        public int hashCode() {
            return key == null ? 0 : key % 3;
        }
    }
}
