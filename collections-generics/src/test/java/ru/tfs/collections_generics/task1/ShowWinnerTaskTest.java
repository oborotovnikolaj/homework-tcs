package ru.tfs.collections_generics.task1;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class ShowWinnerTaskTest {

    @Test
    public void showWinnerTest() throws IOException {
        Map.Entry<String, Integer> winner1 = runShowWinnerTest(new File("src/test/resources/task1/empty_game.txt"));
        Map.Entry<String, Integer> expectedWinner1 = new SimpleEntry<>(null, null);
        Assert.assertEquals(expectedWinner1, winner1);

//        Map.Entry<String, Integer> winner2 = runShowWinnerTest(new File("src/test/resources/task1/game1.txt"));
        Map.Entry<String, Integer> winner2 = runShowWinnerTest(new File("src/test/resources/task1/game2.txt"));
        Map.Entry<String, Integer> expectedWinner2 =
                new SimpleEntry<>("Petr", 16);
        Assert.assertEquals(expectedWinner2, winner2);

        Map.Entry<String, Integer> winner3 = runShowWinnerTest(new File("src/test/resources/task1/last_winner.txt"));
        Map.Entry<String, Integer> expectedWinner3 =
                new SimpleEntry<>("Chuck_Norris", 2147483647);
        Assert.assertEquals(expectedWinner3, winner3);
    }

    private Map.Entry<String, Integer> runShowWinnerTest(File file) throws IOException {
        ShowWinnerTask.GameProcessor gameProcessor = new ShowWinnerTask.GameProcessor(Files.readAllLines(file.toPath()));
        SimpleEntry<String, Integer> winnerEntry = new SimpleEntry<>(gameProcessor.getWinnerName(), gameProcessor.getWinnerResult());
        return winnerEntry;
    }

}