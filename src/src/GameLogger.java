package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.*;
import java.util.*;

public class GameLogger {
    private static final int MAX_SCORES = 10;
    private static final String FILE_NAME = "game_results.log";

    public static void logResult(String result) {
        PriorityQueue<PlayerScore> scores = new PriorityQueue<>(Comparator.comparingInt(PlayerScore::getScore));
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(new PlayerScore(parts[0], Integer.parseInt(parts[1])));
                    if (scores.size() > MAX_SCORES) {
                        scores.poll();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scores.add(new PlayerScore(result.split(",")[0], Integer.parseInt(result.split(",")[1])));
        if (scores.size() > MAX_SCORES) {
            scores.poll();
        }

        List<PlayerScore> sortedScores = new ArrayList<>(scores);
        sortedScores.sort(Comparator.comparingInt(PlayerScore::getScore).reversed());

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (PlayerScore score : sortedScores) {
                writer.println(score.getPlayer() + "," + score.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PlayerScore {
        private final String player;
        private final int score;

        public PlayerScore(String player, int score) {
            this.player = player;
            this.score = score;
        }

        public String getPlayer() {
            return player;
        }

        public int getScore() {
            return score;
        }
    }
}