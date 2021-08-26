package ru.tfs.collections_generics.task2;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static ru.tfs.collections_generics.task2.VeryLargeListTask.getMax10;

public class VeryLargeListTaskTest {

    @Test
    public void getMax10Test() {
        List<Integer> input = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50_000_000; i++) {
            input.add(random.nextInt());
        }

        long start1 = System.currentTimeMillis();
        List<Integer> actualMax = getMax10(input);
        long time1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<Integer> sortedInput = input.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        List<Integer> expectedMax = sortedInput.subList(0, 10);
        long time2 = System.currentTimeMillis() - start2;

        System.out.println("Бысрее в " + time2/time1);
        System.out.println("Быстрее на " + (time2 - time1));

        Assert.assertArrayEquals(expectedMax.toArray(), actualMax.toArray());

        input.clear();
        for (int i = 0; i < 50; i++) {
            input.add(random.nextInt());
        }
        actualMax = getMax10(input);
        sortedInput = input.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        expectedMax = sortedInput.subList(0, 10);
        Assert.assertArrayEquals(expectedMax.toArray(), actualMax.toArray());

        input.clear();
        for (int i = 0; i < 10; i++) {
            input.add(random.nextInt());
        }
        actualMax = getMax10(input);
        sortedInput = input.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        expectedMax = sortedInput.subList(0, 10);
        Assert.assertArrayEquals(expectedMax.toArray(), actualMax.toArray());

        input.clear();
        for (int i = 0; i < 5; i++) {
            input.add(random.nextInt());
        }
        actualMax = getMax10(input);
        sortedInput = input.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        expectedMax = sortedInput.subList(0, 5);
        Assert.assertArrayEquals(expectedMax.toArray(), actualMax.toArray());
    }

}