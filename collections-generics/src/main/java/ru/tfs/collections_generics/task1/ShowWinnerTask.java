package ru.tfs.collections_generics.task1;

import com.sun.istack.internal.Nullable;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowWinnerTask {

    public static void showWinner(List<String> competitors) {
        GameProcessor gameProcessor = new GameProcessor(competitors);
        String winner = gameProcessor.getWinnerName();
        System.out.println("победитель: " + winner == null ? "...ops" : winner);
    }

    public static class GameProcessor {
        private final Map<String, Integer> gameStatistics = new HashMap<>();
        private Map.Entry<String, Integer> winner;

        public GameProcessor(List<String> competitors) {
            competitors.stream()
                    .map(event -> event.split(" "))
                    .forEach(this::processGameEvent);
        }

        private void processGameEvent(String[] gameEvent) {
            Integer value = gameStatistics.merge(gameEvent[0], Integer.valueOf(gameEvent[1]), Integer::sum);
            if (winner == null || winner.getValue() < value) {
                this.winner = new AbstractMap.SimpleEntry<>(gameEvent[0], value);
            }
        }

        public @Nullable String getWinnerName() {
            return this.winner == null ? null : this.winner.getKey();
        }

        public @Nullable Integer getWinnerResult() {
            return this.winner == null ? null : this.winner.getValue();
        }
    }

}
