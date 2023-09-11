package org.example;

import java.util.Random;

public class SequenceSum {
    public static void main(String[] args) throws InterruptedException {
        int sum = 0;
        int[] numbers = new Random().ints(1000, 1, 100).toArray();

        long timeStart = System.currentTimeMillis();
        for (int number : numbers) {
            sum += number;
            Thread.sleep(1);
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("result: " + sum);
        Runtime runtime = Runtime.getRuntime();
        System.out.println("used memory: " + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("sequence method: " + (timeEnd - timeStart) / 100 + " seconds");
    }
}