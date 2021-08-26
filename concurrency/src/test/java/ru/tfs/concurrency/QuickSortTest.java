package ru.tfs.concurrency;

import org.junit.jupiter.api.*;
import ru.tfs.concurrency.merge_sort.*;
import ru.tfs.concurrency.merge_sort.array.ForkJoinQuickSortArray;
import ru.tfs.concurrency.merge_sort.array.ForkJoinQuickSortIntArray;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Бенчмарки писать пока не хочется
 *forkJoinQuickSortArrayTest 4264 - параметризованный массив, форк джоин
 * ForkJoinQuickSortIntArrayTest 1172 - массив из интов, форк джоин
 * mergeSortTest 910 - массив из интов, форк джоин
 * forkJoinQuickSortTest 3605 - ArrayList параметриованный и форк джоин
 * recursiveQuickSortTest 5736 - рекурсия и ArrayList параметриованный
 * quickSortThreadTest 5387 - многопоточный и ArrayList параметриованный
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuickSortTest {

    public static int TEST_SIZE = 1;

    private List<Integer> actual;
    private List<Integer> expected;
    private QuickSort quickSort;

    @BeforeAll
    public void beforeTest() {
        actual = generateRandomArray(TEST_SIZE);
        expected = new ArrayList<>(actual);
        Collections.sort(expected);
    }

    @Test
    @Order(1)
    public void recursiveQuickSortTest() {
        ArrayList<Integer> integers = new ArrayList<>(actual);
        quickSort = new RecursiveQuickSort<>(integers);
        long l = System.currentTimeMillis();
        quickSort.sort();
        System.out.println("Рекурсивная (recursiveQuickSortTest)                                 " + (System.currentTimeMillis() - l));
        Assertions.assertEquals(expected, integers);
    }

    @Test
    @Order(2)
    public void quickSortThreadTest() {
        ArrayList<Integer> integers = new ArrayList<>(actual);
        quickSort = new QuickSortThread<>(integers);
        long l = System.currentTimeMillis();
        quickSort.sort();
        System.out.println("Многопоточная (quickSortThreadTest                                   " + (System.currentTimeMillis() - l));
        Assertions.assertEquals(expected, integers);
    }

    @Test
    @Order(3)
    public void forkJoinQuickSortTest() {
        ArrayList<Integer> integers = new ArrayList<>(actual);
        quickSort = new ForkJoinQuickSort<>(integers, 0, integers.size());
        long l = System.currentTimeMillis();
        quickSort.sort();
        System.out.println("Форк джоин список (forkJoinQuickSortTest)                            " + (System.currentTimeMillis() - l));
        Assertions.assertEquals(expected, integers);
    }

    @Test
    @Order(4)
    public void forkJoinQuickSortArrayTest() {
        Integer[] integers = actual.toArray(new Integer[0]);
        quickSort = new ForkJoinQuickSortArray<>(integers, 0, actual.size());
        long l = System.currentTimeMillis();
        quickSort.sort();
        System.out.println("Форк джоин массив (forkJoinQuickSortArrayTest)                       " + (System.currentTimeMillis() - l));
        Assertions.assertEquals(expected, Arrays.asList(integers));
    }

    @Test
    @Order(5)
    public void forkJoinQuickSortIntArrayTest() {
        int[] ints = actual.stream().mapToInt(i -> (int) i).toArray();
        long l = System.currentTimeMillis();
        new ForkJoinQuickSortIntArray(ints, 0, actual.size()).sort();
        System.out.println("Форк джоин массив без параметризации (ForkJoinQuickSortIntArrayTest) " + (System.currentTimeMillis() - l));
        Assertions.assertEquals(expected, Arrays.stream(ints).boxed().collect(Collectors.toList()));
    }

    @Test
    @Order(6)
    public void mergeSortTest() {
        int[] ints = actual.stream().mapToInt(i -> (int) i).toArray();
        long l = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new MergeSort(ints, 0, ints.length)).join();
        System.out.println("Форк джоин массив без параметризации  (mergeSortTest)                " + (System.currentTimeMillis() - l));
        Assertions.assertEquals(expected, Arrays.stream(ints).boxed().collect(Collectors.toList()));
    }



    public List<Integer> generateRandomArray(int n){
        List<Integer> list = new ArrayList<>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list.add(random.nextInt(1000));
        }
        return list;
    }
}
