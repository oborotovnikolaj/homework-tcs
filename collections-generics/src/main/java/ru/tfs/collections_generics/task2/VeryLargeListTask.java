package ru.tfs.collections_generics.task2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class VeryLargeListTask {

    public static List<Integer> getMax10(List<Integer> veryLargeList) {
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(numOfThreads);
        MyFork myFork = new MyFork(veryLargeList, 0, veryLargeList.size(), veryLargeList.size() / numOfThreads);
        List<Integer> result = forkJoinPool.invoke(myFork);
        return result.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    static private class MyFork extends RecursiveTask<List<Integer>> {

        private List<Integer> values;
        private int from;
        private int to;

        private int subListSize;

        public MyFork(List<Integer> values, int from, int to, int subListSize) {
            this.values = values;
            this.from = from;
            this.to = to;
            this.subListSize = subListSize;
        }

        @Override
        protected List<Integer> compute() {
//            условие того, что задача разбита достаточно
            if ((to - from) <= subListSize) {
                return processSubTask(values.subList(from, to));
//                Если разбили недостаточно на подзадачи
            } else {
                int middle = (to + from) / 2;
                MyFork firstHalf = new MyFork(this.values, from, middle, this.subListSize);
                firstHalf.fork();
                MyFork secondHalf = new MyFork(this.values, middle, to, this.subListSize);

                List<Integer> firstHalfResult = secondHalf.compute();
                List<Integer> secondHalfResult = firstHalf.join();

                return processSubTask(firstHalfResult, secondHalfResult);
            }
        }

        private List<Integer> processSubTask(Iterable<Integer>... subLists) {
            PriorityQueue<Integer> subResult = new PriorityQueue<>(10);
            for (Iterable<Integer> sublist : subLists) {
                for (Integer value : sublist) {
                    if (value == null) {
                        continue;
                    }
                    if (subResult.peek() == null || subResult.size() < 10 || subResult.peek() < value) {
                        if (subResult.size() == 10) {
                            subResult.poll();
                        }
                        subResult.add(value);
                    }
                }
            }
            return Arrays.asList(subResult.toArray(new Integer[subResult.size()]));
        }
    }

}
