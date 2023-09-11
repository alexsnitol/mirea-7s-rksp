package org.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadSum {
    public static void main(String[] args) {
        int[] numbers = new Random().ints(1000, 1, 100).toArray();
        AtomicInteger sum = new AtomicInteger();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        long timeStart = System.currentTimeMillis();
        for (int number : numbers) {
            executorService.execute(() -> {
//                try {
                sum.addAndGet(number);
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            });
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("result: " + sum);
        Runtime runtime = Runtime.getRuntime();
        System.out.println("used memory: " + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("multithread method: " + (timeEnd - timeStart) / 100 + " seconds");
    }
}