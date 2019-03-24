package com.github.arugal.example.agent;

/**
 * @author: zhangwei
 * @date: 11:36/2019-03-24
 */
public class ByteBuddyMainUp {


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new CallTimed().print();
        long end = System.currentTimeMillis();
        System.out.println("start:"+start+"/end:"+end);
    }

}
