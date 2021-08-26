package ru.tfs.concurrency.merge_sort.array;

import ru.tfs.concurrency.merge_sort.QuickSort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinQuickSortIntArray extends RecursiveAction implements QuickSort {

    private final int[] collectionToSort;

    private final int from;
    private final int to;

    public ForkJoinQuickSortIntArray(int[] collectionToSort, int from, int to) {
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
    protected void compute() {

        int size = to - from;

//        if (size <= 8) {
        if (size <= 1) {
            Arrays.sort(collectionToSort, from, to);
        } else {

            int middle = from + size / 2;

            ForkJoinQuickSortIntArray lessHalf = new ForkJoinQuickSortIntArray(collectionToSort, from, middle);
            ForkJoinQuickSortIntArray moreHalf = new ForkJoinQuickSortIntArray(collectionToSort, middle, to);
            lessHalf.fork();
            moreHalf.compute();

            lessHalf.join();
            merge(middle);
        }
    }

    private void merge(int middle) {

        if (collectionToSort[middle] > (collectionToSort[middle - 1])) {
            return;
        }

        int[] copy = Arrays.copyOfRange(collectionToSort, from, to);

        int leftIndex = 0;
        int rightIndex = middle - from;
        int middleInCopy = copy.length / 2;
        int copySize = copy.length;

        for (int i = from; i < to; ++i) {
            if (rightIndex >= copySize || (middleInCopy > leftIndex && copy[leftIndex] < copy[rightIndex])) {
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
