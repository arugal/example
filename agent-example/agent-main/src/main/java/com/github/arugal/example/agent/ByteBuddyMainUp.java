package com.github.arugal.example.agent;

/**
 * @author: zhangwei
 * @date: 11:36/2019-03-24
 */
public class ByteBuddyMainUp {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        long start = System.currentTimeMillis();
        Class c = Class.forName("com.github.arugal.example.agent.CallTimed");
        CallTimed callTimed = (CallTimed) c.newInstance();
        callTimed.print();
        long end = System.currentTimeMillis();
        System.out.println("start:" + start + "/end:" + end);
    }

}
