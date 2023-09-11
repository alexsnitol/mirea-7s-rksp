package org.example;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadSquare {

    private static final ExecutorService service = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            calculateNumber(scanner.nextInt());
        }
    }

    public static void calculateNumber(int n) {
        service.submit(() -> {
            try {
                Thread.sleep(new Random().nextInt(1000, 5001));
                System.out.println(n + "^2 = " + n * n);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}