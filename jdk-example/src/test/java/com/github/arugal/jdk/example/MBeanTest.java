package com.github.arugal.jdk.example;

import org.junit.Test;

import java.lang.management.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhangwei
 */
public class MBeanTest {

    @Test
    public void showJvmInfo() {
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        System.out.println("jvm vendor:" + mxbean.getVmVendor());
        System.out.println("jvm name:" + mxbean.getVmName());
        System.out.println("jvm version:" + mxbean.getVmVersion());
        System.out.println("jvm bootClassPath:" + mxbean.getBootClassPath());
        System.out.println("jvm start time:" + mxbean.getStartTime());
    }

    @Test
    public void showMemoryInfo() {
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        System.out.println("Heap committed:" + heap.getCommitted() + " init:" + heap.getInit() + " max:"
                + heap.getMax() + " used:" + heap.getUsed());
    }

    @Test
    public void showSystem() {
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Architecture: " + op.getArch());
        System.out.println("Processors: " + op.getAvailableProcessors());
        System.out.println("System name: " + op.getName());
        System.out.println("System version: " + op.getVersion());
        System.out.println("Last minute load: " + op.getSystemLoadAverage());
    }

    @Test
    public void showClassLoading(){
        ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
        System.out.println("TotalLoadedClassCount: " + cl.getTotalLoadedClassCount());
        System.out.println("LoadedClassCount" + cl.getLoadedClassCount());
        System.out.println("UnloadedClassCount:" + cl.getUnloadedClassCount());
    }

    @Test
    public void showThread(){
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        System.out.println("ThreadCount:" + thread.getThreadCount());
        System.out.println("AllThreadIds:" + Arrays.toString(thread.getAllThreadIds()));
        System.out.println("CurrentThreadUserTime:" + thread.getCurrentThreadUserTime());
        //......还有其他很多信息
    }

    @Test
    public void showGarbageCollector(){
        List<GarbageCollectorMXBean> gc = ManagementFactory.getGarbageCollectorMXBeans();
        HashSet younggcAlgorithm = new HashSet() {
            {
                this.add("Copy");
                this.add("ParNew");
                this.add("PS Scavenge");
            }
        };
        HashSet oldgcAlgorithm = new HashSet() {
            {
                this.add("MarkSweepCompact");
                this.add("PS MarkSweep");
                this.add("ConcurrentMarkSweep");
            }
        };
        for(GarbageCollectorMXBean GarbageCollectorMXBean : gc){
            String gcAlgorithm = GarbageCollectorMXBean.getName();
            System.out.println("name:" + gcAlgorithm);
            System.out.println("CollectionCount:" + GarbageCollectorMXBean.getCollectionCount());
            System.out.println("CollectionTime" + GarbageCollectorMXBean.getCollectionTime());
            if(younggcAlgorithm.contains(gcAlgorithm)) {
                System.out.println("YoungGCCount:" + GarbageCollectorMXBean.getCollectionCount());
                System.out.println("YoungGCTime" + GarbageCollectorMXBean.getCollectionTime());
            } else if(oldgcAlgorithm.contains(gcAlgorithm)) {
                System.out.println("FullGCCount:" + GarbageCollectorMXBean.getCollectionCount());
                System.out.println("FullGCTime" + GarbageCollectorMXBean.getCollectionTime());
            }
        }
    }

    @Test
    public void showMemoryManager(){
        List<MemoryManagerMXBean> mm = ManagementFactory.getMemoryManagerMXBeans();
        for(MemoryManagerMXBean eachmm: mm){
            System.out.println("name:" + eachmm.getName());
            System.out.println("MemoryPoolNames:" + Arrays.toString(eachmm.getMemoryPoolNames()));
        }
    }

    @Test
    public void showMemoryPool(){
        List<MemoryPoolMXBean> mps = ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean mp : mps){
            System.out.println("name:" + mp.getName());
            System.out.println("CollectionUsage:" + mp.getCollectionUsage());
            System.out.println("type:" + mp.getType());
        }
    }

}
