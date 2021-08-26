package ru.tfs.concurrency.merge_sort;

import java.util.*;

public class RecursiveQuickSort<T extends Comparable<T>> implements QuickSort {

    private final List<T> collectionToSort;

    public RecursiveQuickSort(List<T> collectionToSort) {
        this.collectionToSort = collectionToSort;
    }

    @Override
    public void sort() {
        if (collectionToSort.size() <= 1) {
            return;
        }

        T barrier = collectionToSort.get(0);
        List<T> less = new ArrayList<>();
        List<T> more = new ArrayList<>();
        List<T> equal = new ArrayList<>();
        for (T element : collectionToSort) {
            if (barrier.compareTo(element) < 0) {
                more.add(element);
            }
            if (barrier.compareTo(element) == 0) {
                equal.add(element);
            }
            if (barrier.compareTo(element) > 0) {
                less.add(element);
            }
        }
        new RecursiveQuickSort<>(less).sort();
        new RecursiveQuickSort<>(more).sort();

        int startIndex = 0;
        updateList(less, startIndex);
        startIndex += less.size();
        updateList(equal, startIndex);
        startIndex += equal.size();
        updateList(more, startIndex);

    }

    private void updateList(List<T> update, int indexToStart) {
        int index = indexToStart;
        for (T element : update) {
            collectionToSort.set(index, element);
            index++;
        }
    }
}
