package src;

import utils.*;

public class gameLoop implements Runnable {
    private gamePanel gamePanel;

    public gameLoop(gamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while (true) {
            // Update game state
            for (monster monster : gamePanel.getMonsters()) {
                monster.move();
            }
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
