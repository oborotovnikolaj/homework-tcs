package ru.tfs.concurrency.merge_sort;

import ru.tfs.concurrency.merge_sort.array.ForkJoinQuickSortIntArray;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * This program is based on MergeSort.java in OpenJDK 7 example.
 */
//Не мой код, взял на гит хабе, чтобы проверить скорость
public class MergeSort extends RecursiveAction {

    public static void main(String[] args) {
//        int n = Integer.parseInt(args[0]);
        int n = 10_000_000;
        Random random = new Random(0);
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = random.nextInt();
        }
        long start = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool();

        long start2 = System.nanoTime();
        pool.submit(new ForkJoinQuickSortIntArray(array, 0, array.length)).join();

        System.out.println(String.format("%f [msec]", (System.nanoTime() - start2) / 1000000.0));

        array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = random.nextInt();
        }
        long start3 = System.nanoTime();
        pool.submit(new MergeSort(array, 0, array.length)).join();
        System.out.println(String.format("%f [msec]", (System.nanoTime() - start3) / 1000000.0));

        for (int i = 0; i < array.length; i++) {
            if(i + 1 < array.length && (array[i] > array[i + 1])) {
                throw new RuntimeException("SHit");
            }
        }
    }

    private final int[] array;
    private final int low;
    private final int high;

    public MergeSort(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    public void compute() {
        int size = high - low;
        if (size <= 8) {
//        if (size <= 1) {
            Arrays.sort(array, low, high);
        }
        else {
            int middle = low + (size >> 1);
            invokeAll(new MergeSort(array, low, middle), new MergeSort(array, middle, high));
            merge(middle);
        }
    }

    private void merge(int middle) {
        if (array[middle - 1] < array[middle]) {
            return;
        }
        int copySize = high - low;
        int[] copy = new int[copySize];
        System.arraycopy(array, low, copy, 0, copy.length);

        int middleInCopy = middle - low;
        int indexLeft = 0;
        int indexRight = middleInCopy;

        for (int i = low; i < high; ++i) {
            if (indexRight >= copySize || (indexLeft < middleInCopy && copy[indexLeft] < copy[indexRight])) {
                array[i] = copy[indexLeft];
                indexLeft++;
            }
            else {
                array[i] = copy[indexRight];
                indexRight++;
            }
        }
    }
}