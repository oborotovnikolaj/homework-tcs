package ru.tfs.concurrency.merge_sort.array;

import ru.tfs.concurrency.merge_sort.QuickSort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinQuickSortArray<T extends Comparable<T>> extends RecursiveTask<T> implements QuickSort {

    private final T[] collectionToSort;

    private int from;
    private int to;

    public ForkJoinQuickSortArray(T[] collectionToSort, int from, int to) {
        this.collectionToSort = collectionToSort;
        this.from = from;
        this.to = to;
    }

    @Override
    public void sort() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(this).join();
    }

    @Override
    protected T compute() {

        int size = to - from;

        if (size <= 8) {
            Arrays.sort(collectionToSort, from, to);
            return null;
        }

        int middle = from + size / 2;

        ForkJoinQuickSortArray<T> lessHalf = new ForkJoinQuickSortArray<>(collectionToSort, from, middle);
        ForkJoinQuickSortArray<T> moreHalf = new ForkJoinQuickSortArray<>(collectionToSort, middle, to);

        lessHalf.fork();
        moreHalf.compute();

        lessHalf.join();
        merge(middle);

        return null;
    }

    private void merge(int middle) {

        if (collectionToSort[middle].compareTo(collectionToSort[middle - 1]) > 0) {
            return;
        }

        T[] copy = Arrays.copyOfRange(collectionToSort, from, to);

        int leftIndex = 0;
        int rightIndex = middle - from;
        int middleInCopy = copy.length / 2;

        for (int i = from; i < to; i++) {
            if (rightIndex >= copy.length || middleInCopy > leftIndex && copy[leftIndex].compareTo(copy[rightIndex]) < 0) {
//                collectionToSort.set(i, copy.get(leftIndex++));
                collectionToSort[i] = copy[(leftIndex)];
                leftIndex++;
            } else {
//                collectionToSort.set(i, copy.get(rightIndex++));
                collectionToSort[i] = copy[rightIndex];
                rightIndex++;
            }
        }

    }
}
