package org.example;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinSum {
    public static void main(String[] args) {
        int[] numbers = new Random().ints(1000, 1, 100).toArray();
        AtomicInteger sum = new AtomicInteger();
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        long timeStart = System.currentTimeMillis();
        for (int number : numbers) {
            forkJoinPool.execute(() -> {
//                try {
                sum.addAndGet(number);
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            });
        }
        forkJoinPool.shutdown();
        while (!forkJoinPool.isTerminated()) {
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("result: " + sum);
        Runtime runtime = Runtime.getRuntime();
        System.out.println("used memory: " + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("fork join method: " + (timeEnd - timeStart) / 100 + " seconds");
    }
}