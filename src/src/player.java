package src;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.Point;

import utils.*;

public class player implements KeyListener {
    private int x, y, dx = 0, dy = 0;
    private boolean isSafe = true;
    private int stepSize = 10; // Size of each step in pixels
    private gamePanel gamePanel;

    public player(gamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.START_X_player;
        this.y = Constants.START_Y_player;
        this.isSafe = true;
    }

    public void move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;

        // Update player position
        x += dx * stepSize;
        y += dy * stepSize;
        // Check if player is in a safe zone
        checkSafeZone();
        // If player is not in a safe zone, continue moving
        while (!isSafe && !isMonsterCaught()) {
            x += dx * stepSize;
            y += dy * stepSize;
            checkSafeZone();

            // Add delay for movement if necessary
            try {
                Thread.sleep(100); // Adjust sleep duration as needed
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            move(-1, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            move(1, 0);
        } else if (key == KeyEvent.VK_UP) {
            move(0, -1);
        } else if (key == KeyEvent.VK_DOWN) {
            move(0, 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed for key release in this context
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed for key typed in this context
    }

    // Method to check if the player is in a safe zone
    private void checkSafeZone() {
        // Check if the current cell is occupied
        isSafe = gamePanel.isOccupied(x, y);
    }

    // Method to check if the monster has caught the player
    private boolean isMonsterCaught() {
        // Implement logic to check if the monster has caught the player
        return false;
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    // Getters and setters for x, y, and other player properties
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

    public boolean isInSafeZone() {
        return isSafe;
    }

    public void setInSafeZone(boolean inSafeZone) {
        this.isSafe = inSafeZone;
    }
    public void conquerArea(int dx, int dy) {
        // Start from the current position
        int startX = x;
        int startY = y;

        // List to store the path taken by the player/rival
        List<Point> path = new ArrayList<>();

        // Move in the specified direction until reaching a safe zone or caught by a monster
        while (!isSafe && !isMonsterCaught()) {
            // Mark the current cell as occupied
            path.add(new Point(x, y));

            // Move to the next cell
            x += dx * stepSize;
            y += dy * stepSize;

            // Check if the player/rival is in a safe zone or caught by a monster
            checkSafeZone();
            if (isMonsterCaught()) {
                // If caught by a monster, stop conquering and return
                return;
            }
        }

        // If a safe zone is reached, fill the enclosed area
        gamePanel.fillArea(path, this instanceof player ? Color.BLUE : Color.RED);
    }

}


