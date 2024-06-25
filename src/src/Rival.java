package src;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import utils.*;

public class Rival implements KeyListener, Runnable {
    private int x, y, dx = 0, dy = 0;
    private boolean isSafe;
    private int stepSize = 1; // Size of each step in pixels
    private GamePanel gamePanel;
    private boolean isComputerControlled = false; // Flag for AI control

    public Rival(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.START_X_RIVAL;
        this.y = Constants.START_Y_RIVAL;
    }

    public void move() {
        if (isComputerControlled) {
            // Implement simple AI algorithm for movement
            // For now, this method is empty
        } else if (dx != 0 || dy != 0) {
            int newX = this.x + dx * stepSize;
            int newY = this.y + dy * stepSize;

            // Check if the new position is within the game area
            if (newX >= 0 && newX < Constants.GRID_WIDTH && newY >= 0 && newY < Constants.GRID_HEIGHT) {
                this.setX(newX);
                this.setY(newY);
            }
            // Check if rival is in a safe zone
            checkSafeZone();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isComputerControlled) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_A) {
                dx = -1;
                dy = 0;
            } else if (key == KeyEvent.VK_D) {
                dx = 1;
                dy = 0;
            } else if (key == KeyEvent.VK_W) {
                dx = 0;
                dy = -1;
            } else if (key == KeyEvent.VK_S) {
                dx = 0;
                dy = 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        dx = 0;
        dy = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed for key typed in this context
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Method to check if the rival is in a safe zone
    private void checkSafeZone() {
        // Check if the current cell is occupied
        isSafe = gamePanel.isOccupied(x, y);
    }

    private boolean isMonsterCaught() {
        // Iterate over the list of monsters
        for (monster monster : gamePanel.getMonsters()) {
            // If a monster is at the same position as the player/rival, return true
            if (Math.abs(monster.getX() - this.x) <= 2 && Math.abs(monster.getY() - this.y) <= 2) {
                return true;
            }
        }

        // If no monster is at the same position as the player/rival, return false
        return false;
    }

    // Getter and setter for computer control
    public boolean isComputerControlled() {
        return isComputerControlled;
    }

    public void setComputerControlled(boolean isComputerControlled) {
        this.isComputerControlled = isComputerControlled;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }
}
