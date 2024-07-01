package src;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Point;

import utils.*;

/**
 * The {@code Player} class defines the properties and behavior of the game's player.
 * It handles movement based on keyboard input and interacts with the game environment.
 */
public class Player implements KeyListener, Runnable {
    private int x, y; // Current position of the player
    private int dx = 0, dy = 0; // Direction of movement along X and Y axes
    private boolean isSafe = true; // Indicates whether the player is in a safe zone
    private int stepSize = 1; // Size of each step in pixels
    private GamePanel gamePanel; // Reference to the game panel for interaction
    private List<Point> path = new CopyOnWriteArrayList<>(); // Tracks the path taken by the player
    private int score; // Player's current score

    /**
     * Constructs a Player with a reference to the game panel and initializes its starting position.
     *
     * @param gamePanel The game panel which contains this player and is used for interaction
     */
    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.START_X_player;
        this.y = Constants.START_Y_player;
        this.isSafe = true;
    }

    /**
     * Returns the color representing the player, typically used in rendering.
     *
     * @return The color of the player
     */
    public Color getColor() {
        return Color.BLUE;
    }

    /**
     * Moves the player based on the current direction of movement.
     * Checks boundaries and updates the player's position accordingly.
     */
    public void move() {
        if (this.dx != 0 || this.dy != 0) {
            int newX = this.x + this.dx * stepSize;
            int newY = this.y + this.dy * stepSize;

            // Ensure the new position is within the game area
            if (newX >= 0 && newX < Constants.GRID_WIDTH && newY >= 0 && newY < Constants.GRID_HEIGHT) {
                this.setX(newX);
                this.setY(newY);

                // Track the player's path outside of safe zones
                if (!isInSafeZone()) {
                    path.add(new Point(newX, newY));
                }
            }
        }
    }

    /**
     * Returns the path taken by the player.
     *
     * @return The list of points representing the path
     */
    public List<Point> getPath() {
        return path;
    }

    /**
     * Clears the player's path and resets the safety status.
     */
    public void clearPath() {
        path.clear();
        isSafe = false;
    }

    /**
     * Handles key press events to control the player's movement.
     *
     * @param e The key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:  // Move left
                this.dx = -1;
                this.dy = 0;
                break;
            case KeyEvent.VK_RIGHT: // Move right
                this.dx = 1;
                this.dy = 0;
                break;
            // Add other cases to handle up and down movements
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // this.dx = 0;
        // this.dy = 0;
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

    // Method to check if the monster has caught the player
    private boolean isMonsterCaught() {
        // Iterate over the list of monsters
        for (Monster monster : gamePanel.getMonsters()) {
            // If a monster is at the same position +- 2px as the player/rival, return true
            if (Math.abs(monster.getX() - this.x) <= 2 && Math.abs(monster.getY() - y) <= 2) {
                return true;
            }
        }

        // If no monster is at the same position as the player/rival, return false
        return false;
    }

    public void draw(Graphics g) {

        // Draw the path
        g.setColor(Constants.PLAYER_TRAIL_COLOR);
        for (Point point : path) {
            g.fillRect(point.x * Constants.CELL_SIZE, point.y * Constants.CELL_SIZE, Constants.CELL_SIZE,
                    Constants.CELL_SIZE);
        }
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

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean isInSafeZone() {
        int borderSize = 1; // Size of the border in pixels

        return x < borderSize || x > Constants.GRID_WIDTH - 1 - borderSize ||
                y < borderSize || y > Constants.GRID_HEIGHT - 1 - borderSize || gamePanel.isOccupied(x, y);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
