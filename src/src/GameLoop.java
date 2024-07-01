package src;

import javax.swing.JOptionPane;

import utils.*;

/**
 * The {@code GameLoop} class implements the game loop mechanism. It continuously updates
 * the game state and re-renders the game panel until the game is over.
 */
public class GameLoop implements Runnable {
    private GamePanel gamePanel;  // The game panel to be updated and rendered
    private Runnable task;  // The task to be executed in the game loop, usually updating game state

    /**
     * Constructs a {@code GameLoop} with a specified game panel and a task.
     * This sets up the loop with the components necessary for game execution.
     *
     * @param gamePanel The {@code GamePanel} object that represents the game display
     * @param task      The {@code Runnable} task that updates the game state
     */
    public GameLoop(GamePanel gamePanel, Runnable task) {
        this.gamePanel = gamePanel;
        this.task = task;
    }

    /**
     * The run method is executed when the game loop starts. It continually performs the task,
     * updates the game panel, and pauses briefly to manage game timing and speed.
     * The loop exits when a game over condition is met.
     */
    @Override
    public void run() {
        while (true) {
            task.run();  // Execute the assigned task, typically updating the game state
            gamePanel.repaint();  // Re-render the game panel

            // Check for game over condition
            if (gamePanel.getGameOver() != -1) {
                break;  // Exit the loop if game is over
            }

            // Sleep for a bit to control the game speed
            try {
                Thread.sleep(100);  // Sleep for 100 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
