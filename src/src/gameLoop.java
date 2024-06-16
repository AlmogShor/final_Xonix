package src;

import utils.*;

public class gameLoop implements Runnable {
    private gamePanel gamePanel;
    private Runnable task;

    public gameLoop(gamePanel gamePanel, Runnable task) {
        this.gamePanel = gamePanel;
        this.task = task;
    }

    @Override
    public void run() {
        while (true) {
            task.run();
            gamePanel.repaint();

            // Sleep for a bit to control the game speed
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
