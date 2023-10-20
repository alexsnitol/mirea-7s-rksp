package org.example;

import io.reactivex.Flowable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

/**
 * Вариант 21
 * № 2.1.1: Преобразовать поток из 1000 случайных чисел от 0 до 1000 в поток, содержащий квадраты данных чисел.
 * <p>
 * № 2.2.1:
 * Даны два потока по 1000 элементов: первый содержит случайную букву, второй — случайную цифру.
 * Сформировать поток, каждый элемент которого объединяет элементы из обоих потоков.
 * Например, при входных потоках (A, B, C) и (1, 2, 3) выходной поток — (A1, B2, B3).
 * <p>
 * № 2.3.1: Дан поток из 10 случайных чисел. Сформировать поток, содержащий все числа, кроме первых трех.
 */

public class Opt2 {

    public static void main(String[] args) {
        // 2.1.1
        doSquareNumbers();
        // 2.2.1
        doMergeFlows();
        // 2.3.1
        doFilterNumbers();
    }

    private static void doSquareNumbers() {
        List<Integer> numberList = new LinkedList<>();
        int index = 0;
        while (index != 1000) {
            numberList.add(new Random().nextInt(0, 1000));
            index += 1;
        }

        Flowable<Integer> flow = Flowable.fromIterable(numberList).map(n -> n * n);

        List<Integer> numberList2 = new LinkedList<>();
        flow.subscribe(numberList2::add);

        out.println("сформирован  массив квадратов");
    }

    private static void doMergeFlows() {
        List<Character> characterList = new LinkedList<>();
        int index = 0;
        while (index != 1000) {
            characterList.add((char) ((char) new Random().nextInt(26) + 'a'));
            index += 1;
        }

        List<Integer> numberList = new LinkedList<>();
        index = 0;
        while (index != 1000) {
            numberList.add(new Random().nextInt(0, 9));
            index += 1;
        }

        Flowable<Character> charFlow = Flowable.fromIterable(characterList);
        Flowable<Integer> intFlow = Flowable.fromIterable(numberList);
        Flowable<String> zipFlow = Flowable.zip(charFlow, intFlow, (c, i) -> c.toString() + i);

        List<String> mergeList = new LinkedList<>();
        zipFlow.subscribe(mergeList::add);

        out.println("сформирован массив объединений");
    }

    private static void doFilterNumbers() {
        List<Integer> numberList = new LinkedList<>();
        int index = 0;
        while (index != 10) {
            numberList.add(new Random().nextInt(0, 1000));
            index += 1;
        }

        Flowable<Integer> flow = Flowable.fromIterable(numberList);

        List<Integer> numberList2 = new LinkedList<>();
        AtomicInteger index2 = new AtomicInteger(0);
        flow.subscribe(n -> {
            if (index2.get() >= 3) {
                numberList2.add(n);
            }
            index2.addAndGet(1);
        });

        out.println("сформирован массив без первых трёх элементов");
    }

}