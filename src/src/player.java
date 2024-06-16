package src;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.Point;

import utils.*;

public class player implements KeyListener, Runnable {
    private int x, y, dx = 0, dy = 0;
    private boolean isSafe = true;
    private int stepSize = 1; // Size of each step in pixels
    private gamePanel gamePanel;

    public player(gamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.START_X_player;
        this.y = Constants.START_Y_player;
        this.isSafe = true;
    }

    public void move() {
        if (this.dx != 0 || this.dy != 0) {
            int newX = this.x + this.dx * stepSize;
            int newY = this.y + this.dy * stepSize;

            // Check if the new position is within the game area
            if (newX >= 0 && newX < Constants.GRID_WIDTH && newY >= 0 && newY < Constants.GRID_HEIGHT) {
                this.setX(newX);
                this.setY(newY);
            }
            // Check if player is in a safe zone
            checkSafeZone();
            // If player is not in a safe zone, continue moving
            while (!isSafe && !isMonsterCaught()) {
                x += this.dx * stepSize;
                y += this.dy * stepSize;
                checkSafeZone();

                // Add delay for movement if necessary
                try {
                    Thread.sleep(100); // Adjust sleep duration as needed
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.dx = -1;
            this.dy = 0;
        } else if (key == KeyEvent.VK_RIGHT) {
            this.dx = 1;
            this.dy = 0;
        } else if (key == KeyEvent.VK_UP) {
            this.dx = 0;
            this.dy = -1;
        } else if (key == KeyEvent.VK_DOWN) {
            this.dx = 0;
            this.dy = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.dx = 0;
        this.dy = 0;
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

    // Method to check if the player is in a safe zone
    private void checkSafeZone() {
        // Check if the current cell is occupied
        isSafe = gamePanel.isOccupied(x, y);
    }

    // Method to check if the monster has caught the player
    private boolean isMonsterCaught() {
        // Iterate over the list of monsters
        for (monster monster : gamePanel.getMonsters()) {
            // If a monster is at the same position +- 2px as the player/rival, return true
            if (Math.abs(monster.getX() - this.x) <= 2 && Math.abs(monster.getY() - y) <= 2) {
                return true;
            }
        }

        // If no monster is at the same position as the player/rival, return false
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
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

    public void setGamePanel(gamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean isInSafeZone() {
        return this.isSafe;
    }
}
