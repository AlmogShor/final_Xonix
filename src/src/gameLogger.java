package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class gameLogger {
    public static void logResults(List<String> results) {
        try (FileWriter writer = new FileWriter("game_results.log", true)) {
            for (String result : results) {
                writer.write(result + "\n ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
