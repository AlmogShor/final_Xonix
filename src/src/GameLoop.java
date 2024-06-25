package src;

import javax.swing.JOptionPane;

import utils.*;

public class GameLoop implements Runnable {
    private GamePanel gamePanel;
    private Runnable task;

    public GameLoop(GamePanel gamePanel, Runnable task) {
        this.gamePanel = gamePanel;
        this.task = task;
    }

    @Override
    public void run() {
        while (true) {
            task.run();
            gamePanel.repaint();

            if (gamePanel.getGameOver() != -1) {
                break;
            }

            // Sleep for a bit to control the game speed
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
