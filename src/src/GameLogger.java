package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.*;
import java.util.*;

/**
 * The {@code GameLogger} class is responsible for managing the logging of player scores.
 * It maintains a list of top scores and writes them to a log file.
 */
public class GameLogger {
    private static final int MAX_SCORES = 10; // Maximum number of scores to be stored in the log
    private static final String FILE_NAME = "game_results.log"; // Name of the file where scores are stored

    /**
     * Logs a game result into the file. If the list of top scores exceeds the maximum limit,
     * the lowest score is removed. This method ensures that only the top scores are kept.
     *
     * @param result The result string in the format "playerName,score"
     */
    public static void logResult(String result) {
        PriorityQueue<PlayerScore> scores = new PriorityQueue<>(Comparator.comparingInt(PlayerScore::getScore));
        // Read existing scores from the file and populate the priority queue
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    scores.add(new PlayerScore(parts[0], Integer.parseInt(parts[1])));
                    if (scores.size() > MAX_SCORES) {
                        scores.poll(); // Remove the lowest score if the max limit is exceeded
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the new score and remove the lowest if necessary
        scores.add(new PlayerScore(result.split(",")[0], Integer.parseInt(result.split(",")[1])));
        if (scores.size() > MAX_SCORES) {
            scores.poll();
        }

        // Sort scores in descending order and write to the file
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

    /**
     * The {@code PlayerScore} class represents a player's score. It includes the player's name
     * and their score.
     */
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
