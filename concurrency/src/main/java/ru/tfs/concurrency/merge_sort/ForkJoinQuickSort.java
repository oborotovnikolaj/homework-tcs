package ru.tfs.concurrency.merge_sort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinQuickSort<T extends Comparable<T>> extends RecursiveAction implements QuickSort {

    private final List<T> collectionToSort;

    private int from;
    private int to;

    public ForkJoinQuickSort(List<T> collectionToSort, int from, int to) {
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

        if (size <= 1) {
            return;
        }

        int middle = from + size / 2;

        ForkJoinQuickSort<T> lessHalf = new ForkJoinQuickSort<>(collectionToSort, from, middle);
        ForkJoinQuickSort<T> moreHalf = new ForkJoinQuickSort<>(collectionToSort, middle, to);

//        invokeAll(lessHalf, moreHalf);
        lessHalf.fork();
        moreHalf.compute();
        lessHalf.join();

        merge(middle);
    }

    private void merge(int middle) {

        if (collectionToSort.get(middle).compareTo(collectionToSort.get(middle - 1)) > 0) {
            return;
        }

        List<T> copy = new ArrayList<>(collectionToSort.subList(from, to));

        int leftIndex = 0;
        int rightIndex = middle - from;
        int middleInCopy = copy.size()/2;

        for (int i = from; i < to; i++) {
            if (rightIndex >= copy.size() || middleInCopy > leftIndex && copy.get(leftIndex).compareTo(copy.get(rightIndex)) < 0) {
//                collectionToSort.set(i, copy.get(leftIndex++));
                collectionToSort.set(i, copy.get(leftIndex));
                leftIndex++;
            } else {
//                collectionToSort.set(i, copy.get(rightIndex++));
                collectionToSort.set(i, copy.get(rightIndex));
                rightIndex++;
            }
        }

    }
}
