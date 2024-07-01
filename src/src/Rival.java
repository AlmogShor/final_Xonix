package src;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import utils.*;

/**
 * The {@code Rival} class defines the properties and behaviors of the rival in the game.
 * It can operate either under player control or as an AI-controlled entity.
 */
public class Rival implements KeyListener, Runnable {
    private int x, y; // Current position of the rival
    private int dx = 0, dy = 0; // Direction of movement along X and Y axes
    private boolean isSafe; // Indicates whether the rival is in a safe zone
    private int stepSize = 1; // Size of each step in pixels
    private GamePanel gamePanel; // Reference to the game panel for interaction
    private boolean isComputerControlled = false; // Flag to determine if the rival is AI-controlled
    private int score; // Rival's current score
    private List<Point> path = new CopyOnWriteArrayList<>(); // Tracks the path taken by the rival

    /**
     * Constructs a Rival with a reference to the game panel and initializes its starting position.
     *
     * @param gamePanel The game panel which contains this rival and is used for interaction
     */
    public Rival(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.GRID_WIDTH - 1;
        this.y = Constants.GRID_HEIGHT - 1;
        this.isSafe = true;
    }

    /**
     * Returns the color representing the rival, typically used in rendering.
     *
     * @return The color of the rival
     */
    public Color getColor() {
        return Color.RED;
    }

    /**
     * Moves the rival based on the current direction of movement or AI algorithm if computer-controlled.
     * Checks boundaries and updates the rival's position accordingly.
     */
    public void move() {
        if (isComputerControlled) {
            // Implement simple AI algorithm for movement here
        } else if (dx != 0 || dy != 0) {
            int newX = this.x + dx * stepSize;
            int newY = this.y + dy * stepSize;

            // Ensure the new position is within the game area
            if (newX >= 0 && newX < Constants.GRID_WIDTH && newY >= 0 && newY < Constants.GRID_HEIGHT) {
                this.setX(newX);
                this.setY(newY);

                // Track the rival's path outside of safe zones
                if (!isInSafeZone()) {
                    path.add(new Point(newX, newY));
                }
            }
        }
    }

    /**
     * Returns the path taken by the rival.
     *
     * @return The list of points representing the path
     */
    public List<Point> getPath() {
        return path;
    }

    /**
     * Clears the rival's path and resets the safety status.
     */
    public void clearPath() {
        path.clear();
        isSafe = false;
    }

    /**
     * Handles key press events to control the rival's movement.
     *
     * @param e The key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (isComputerControlled) return; // Ignore key presses if AI-controlled

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
        for (Monster monster : gamePanel.getMonsters()) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics g) {
        // Draw the path
        g.setColor(Constants.RIVAL_TRAIL_COLOR);
        for (Point point : path) {
            g.fillRect(point.x * Constants.CELL_SIZE, point.y * Constants.CELL_SIZE, Constants.CELL_SIZE,
                    Constants.CELL_SIZE);
        }
        g.setColor(Color.RED);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    public boolean isInSafeZone() {
        int borderSize = 1; // Size of the border in pixels

        return x < borderSize || x > Constants.GRID_WIDTH - 1 - borderSize ||
                y < borderSize || y > Constants.GRID_HEIGHT - 1 - borderSize || gamePanel.isOccupied(x, y);
    }

}
