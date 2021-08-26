package ru.tfs.concurrency.multiply_matrix;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiplyMatrixTask {

    /**
     * Перемножение матриц однопоточное, при котором не возникает cache missed благодаря
     * транспонированию left матрицы
     */
    public static void multiplySTMatrixFromFiles(File leftMatrixFile, File rightMatrixFile, File result) {
        int[][] left = readMatrixFromFile(leftMatrixFile, false);
        int[][] rightT = readMatrixFromFile(rightMatrixFile, true);
        int[][] resultT = multiplyMatrixST(left, rightT);
        writeMatrixToFile(resultT, result);
    }


    public static int[][] multiplyMatrixST(final int[][] left, final int[][] rightT) {

        final int colCount = rightT.length;
        final int sumLength = rightT[0].length;
        final int rowCount = left.length;

        int[][] resultT = new int[rowCount][colCount];
        for (int row = 0; row < rowCount; ++row) {  // Цикл по строкам матрицы left.
            for (int col = 0; col < colCount; ++col) {  // Цикл по строкам матрицы rightT (столбцам right).
                int sum = 0;
                for (int i = 0; i < sumLength; ++i) { // Цикл по столбцам left/rightT
                    sum += left[row][i] * rightT[col][i];
                }
                resultT[row][col] = sum;
            }
        }

        return resultT;
    }

    /**
     * Перемнжение матриц в многопоточном режиме
     *
     * @param leftMatrixFile
     * @param rightMatrixFile
     */
    public static void multiplyMTMatrixFromFiles(File leftMatrixFile, File rightMatrixFile, File result) {
        int[][] left = readMatrixFromFile(leftMatrixFile, false);
        int[][] rightT = readMatrixFromFile(rightMatrixFile, true);
        int[][] resultT = multiplyMatrixMT(left, rightT, Runtime.getRuntime().availableProcessors());
        writeMatrixToFile(resultT, result);
    }

    public static int[][] multiplyMatrixMT(final int[][] firstMatrix,
                                           final int[][] secondMatrixT,
                                           int threadCount) {
        if (threadCount == 0) {
            throw new RuntimeException("Нужен хотя бы один поток!!!");
        }

        if (firstMatrix == null || secondMatrixT == null || secondMatrixT.length * firstMatrix.length == 0) {
            throw new RuntimeException("Хотя бы одна из матриц пустая. Перемножить нельзя!!!");
        }

        if (firstMatrix[0].length != secondMatrixT[0].length) {
            throw new RuntimeException("Эти матрицы невозможно перемножить!!!");
        }

        final int rowCount = firstMatrix.length;             // Число строк результирующей матрицы.
        final int colCount = secondMatrixT.length;         // Число столбцов результирующей матрицы.

        final int[][] result = new int[colCount][rowCount];  // Результирующая матрица.

        final int cellsForThread = (rowCount * colCount) / threadCount;  // Число вычисляемых ячеек на поток.
        int firstIndex = 0;  // Индекс первой вычисляемой ячейки.
        final MultiplierThread[] multiplierThreads = new MultiplierThread[threadCount];  // Массив потоков.

        // Создание и запуск потоков.
        for (int threadIndex = threadCount - 1; threadIndex >= 0; --threadIndex) {
            int lastIndex = firstIndex + cellsForThread;  // Индекс последней вычисляемой ячейки.
            if (threadIndex == 0) {
                /* Один из потоков должен будет вычислить не только свой блок ячеек,
                   но и остаток, если число ячеек не делится нацело на число потоков. */
                lastIndex = rowCount * colCount;
            }
            multiplierThreads[threadIndex] = new MultiplierThread(firstMatrix, secondMatrixT, result, firstIndex, lastIndex);
            multiplierThreads[threadIndex].start();
            firstIndex = lastIndex;
        }

        // Ожидание завершения потоков.
        try {
            for (final MultiplierThread multiplierThread : multiplierThreads)
                multiplierThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    static private class MultiplierThread extends Thread {

        /**
         * Первая (левая) матрица.
         */
        private final int[][] firstMatrix;
        /**
         * Вторая (правая) матрица.
         */
        private final int[][] secondMatrixT;
        /**
         * Результирующая матрица.
         */
        private final int[][] resultMatrix;
        /**
         * Начальный индекс.
         */
        private final int firstIndex;
        /**
         * Конечный индекс.
         */
        private final int lastIndex;
        /**
         * Число членов суммы при вычислении значения ячейки.
         */
        private final int sumLength;

        public MultiplierThread(final int[][] firstMatrix,
                                final int[][] secondMatrixT,
                                final int[][] resultMatrix,
                                final int from,
                                final int to) {
            this.firstMatrix = firstMatrix;
            this.secondMatrixT = secondMatrixT;
            this.resultMatrix = resultMatrix;
            this.firstIndex = from;
            this.lastIndex = to;

            sumLength = secondMatrixT[0].length;
        }

        @Override
        public void run() {

            assert firstIndex <= lastIndex;

            final int count = firstMatrix.length;
            for (int index = firstIndex; index < lastIndex; ++index) {
                calcValue(index % count, index / count);
            }
        }

        private void calcValue(final int row, final int col) {
            int sum = 0;
            for (int i = 0; i < sumLength; ++i) {
                sum += firstMatrix[row][i] * secondMatrixT[col][i];
            }
            resultMatrix[row][col] = sum;
        }
    }


    public static @Nullable int[][] readMatrixFromFile(File file, boolean needToTranspose) {
        int[][] result = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<List<Integer>> matrixArray = new ArrayList<>();
            String s = br.readLine();
            while (s != null && !s.equals("")) {
                matrixArray.add(
                        Arrays.stream(s.split(" "))
                                .map(Integer::valueOf)
                                .collect(Collectors.toList())
                );
                s = br.readLine();
            }
            result = new int[matrixArray.size()][];
            for (int i = 0; i < matrixArray.size(); i++) {
                result[i] = matrixArray.get(i).stream().mapToInt(Integer::intValue).toArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return needToTranspose ? transposeMatrix(result) : result;
    }

    public static @Nullable int[][] transposeMatrix(@Nullable int[][] matrix) {

        if (matrix == null || matrix.length == 0) {
            return null;
        }

        int[][] matrixT = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrixT[j][i] = matrix[i][j];
            }
        }
        return matrixT;
    }


    public static void writeMatrixToFile(int[][] matrix, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            StringBuilder sb = new StringBuilder();
            for (int[] row : matrix) {
                for (int i = 0; i < row.length; i++) {
                    sb.append(row[i]);
                    if (i != row.length - 1) {
                        sb.append(" ");
                    }
                }
                sb.append("\n");
            }
            bw.write(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
