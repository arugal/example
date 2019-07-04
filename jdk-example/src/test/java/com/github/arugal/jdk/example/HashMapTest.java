package com.github.arugal.jdk.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author: zhangwei
 * @date: 2019-07-02/22:39
 */
public class HashMapTest {

    @Test
    public void test1() {
        HashMap<Hash, Integer> map = new HashMap<>(4);

        for(int i = 0; i < 10; i++) {
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
    public void test4(){
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-2 >> 1)+" "+(-2 >> 1));
        System.out.println(Integer.toBinaryString(-2 >>> 1)+" "+(-2 >>> 1));
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
