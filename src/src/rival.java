package src;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import utils.*;

public class rival implements KeyListener {
    private int x, y, dx = 0, dy = 0;
    private boolean isSafe;
    private int stepSize = 1; // Size of each step in pixels
    private gamePanel gamePanel;
    private boolean isComputerControlled = false; // Flag for AI control

    public rival(gamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.START_X_RIVAL;
        this.y = Constants.START_Y_RIVAL;
    }

    public void move(int dx, int dy) {

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

    public void move() {
        if (isComputerControlled) {
            // Implement simple AI algorithm for movement
            // For now, this method is empty
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isComputerControlled) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_A) {
                move(-1, 0);
            } else if (key == KeyEvent.VK_D) {
                move(1, 0);
            } else if (key == KeyEvent.VK_W) {
                move(0, -1);
            } else if (key == KeyEvent.VK_S) {
                move(0, 1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optionally, you can stop movement on key release
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed for key typed in this context
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

    // Getters and setters for x, y, and other rival properties
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setGamePanel(src.gamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
