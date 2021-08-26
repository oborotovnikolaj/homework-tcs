package ru.tfs.concurrency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ru.tfs.concurrency.multiply_matrix.MultiplyMatrixTask.multiplyMTMatrixFromFiles;
import static ru.tfs.concurrency.multiply_matrix.MultiplyMatrixTask.multiplySTMatrixFromFiles;

class MultiplyMatrixTaskTest {

    @Test
    public void emptyInputMTTest() throws IOException {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            checkMultiplyMTMatrixFromFiles(new File(getClass().getClassLoader().getResource("multiply_matrix_mt/empty").getFile()));
        });
        Assertions.assertEquals(runtimeException.getMessage(), "Хотя бы одна из матриц пустая. Перемножить нельзя!!!");
    }

    @Test
    public void incorrectSizeMTTest() throws IOException {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            checkMultiplyMTMatrixFromFiles(new File("src/test/resources/multiply_matrix_mt/incorrect"));
        });
        Assertions.assertEquals( "Эти матрицы невозможно перемножить!!!", runtimeException.getMessage());
    }

    @Test
    public void simpleCaseMTTest() throws IOException {
        checkMultiplyMTMatrixFromFiles(new File("src/test/resources/multiply_matrix_mt/simple"));
    }

    @Test
    public void complexCaseMTTest() throws IOException {
        checkMultiplyMTMatrixFromFiles(new File("src/test/resources/multiply_matrix_mt/complex"));
    }

    @Test
    public void complexCaseSTTest() throws IOException {
        multiplySTMatrixFromFiles(
                new File("src/test/resources/multiply_matrix_mt/complex/left.txt"),
                new File("src/test/resources/multiply_matrix_mt/complex/right.txt"),
                new File("src/test/resources/multiply_matrix_mt/complex/result.txt")

        );

        List<String> actual = Files.lines(Paths.get("src/test/resources/multiply_matrix_mt/complex/result.txt" ))
                .collect(Collectors.toList());
        List<String> expected = Files.lines(Paths.get("src/test/resources/multiply_matrix_mt/complex/result_expected.txt"))
                .collect(Collectors.toList());

        Assertions.assertLinesMatch(expected, actual);
    }

    private void checkMultiplyMTMatrixFromFiles(File dir) throws IOException {
        multiplyMTMatrixFromFiles(
                new File(dir, "left.txt"),
                new File(dir, "right.txt"),
                new File(dir, "result.txt")
        );
        List<String> actual = Files.lines(Paths.get(dir.getPath(), "result.txt" ))
                .collect(Collectors.toList());
        List<String> expected = Files.lines(Paths.get(dir.getPath(), "result_expected.txt"))
                .collect(Collectors.toList());

        Assertions.assertLinesMatch(expected, actual);
    }



    //ТЕСТЫ КОТОРЫЕ НЕ ОСОБО ОТНОСЯТСЯ К ОСНОВУ КОДУ
    //Проверил про cache miss при перемножении матриц - очень сильно ускорилось

//    @Test
//    public void multiplyMatrixMissedCacheTest() {
//
//        final int[][] firstMatrix = new int[2500][2500];    // Первая (левая) матрица.
//        final int[][] secondMatrix = new int[2500][2500];  // Вторая (правая) матрица.
//
//        randomMatrix(firstMatrix);
//        randomMatrix(secondMatrix);
//
//        long start = System.currentTimeMillis();
//        final int[][] resultMatrix = multiplyMatrixMissedCache(firstMatrix, secondMatrix);
//        System.out.println("Время расчета " + (System.currentTimeMillis() - start));
//    }
//
//    @Test
//    public void multiplyMatrixCorrectCacheTest() {
//
//        final int[][] firstMatrix = new int[2500][2500];    // Первая (левая) матрица.
//        final int[][] secondMatrix = new int[2500][2500];  // Вторая (правая) матрица.
//
//        randomMatrix(firstMatrix);
//        randomMatrix(secondMatrix);
//
//        long start = System.currentTimeMillis();
//        final int[][] resultMatrix = multiplyMatrixCorrectCache(firstMatrix, secondMatrix);
//        System.out.println("Время расчета " + (System.currentTimeMillis() - start));
//    }
//
//    @Test
//    public void multiplyMatrixMTSingleThreadModeTest() {
//
//        final int[][] firstMatrix = new int[2500][2500];    // Первая (левая) матрица.
//        final int[][] secondMatrix = new int[2500][2500];  // Вторая (правая) матрица.
//
//        randomMatrix(firstMatrix);
//        randomMatrix(secondMatrix);
//
//        long start = System.currentTimeMillis();
//        final int[][] resultMatrix = multiplyMatrixMTSingleThreadMode(firstMatrix, secondMatrix);
//        System.out.println("Время расчета " + (System.currentTimeMillis() - start));
//    }
//
//    @Test
//    public void multiplyMatrixMTTest() {
//
//        final int[][] firstMatrix = new int[3000][3000];    // Первая (левая) матрица.
//        final int[][] secondMatrix = new int[3000][3000];  // Вторая (правая) матрица.
//
//        randomMatrix(firstMatrix);
//        randomMatrix(secondMatrix);
//
//        long start = System.currentTimeMillis();
//        final int[][] resultMatrix = multiplyMatrixMT(firstMatrix, secondMatrix, Runtime.getRuntime().availableProcessors());
//        System.out.println("Время расчета " + (System.currentTimeMillis() - start));
//    }
//
//    @Test
//    public void multiplyMatrixMTTest2() {
//
//        final int[][] firstMatrix = new int[3000][3000];    // Первая (левая) матрица.
//        final int[][] secondMatrix = new int[3000][3000];  // Вторая (правая) матрица.
//
//        randomMatrix(firstMatrix);
//        randomMatrix(secondMatrix);
//
//        long start = System.currentTimeMillis();
//        final int[][] resultMatrix = multiplyMatrixMTOld(firstMatrix, secondMatrix, Runtime.getRuntime().availableProcessors());
//        System.out.println("Время расчета " + (System.currentTimeMillis() - start));
//    }


}