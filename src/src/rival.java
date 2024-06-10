package src;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import utils.*;

public class rival implements KeyListener {
    private int x, y, dx = 0, dy = 0;
    private boolean isSafe;
    private int stepSize = 10; // Size of each step in pixels
    private gamePanel gamePanel;
    private boolean isComputerControlled = false; // Flag for AI control

    public rival(gamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = Constants.START_X_RIVAL;
        this.y = Constants.START_Y_RIVAL;
    }

    public void move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;

        // Update rival position
        x += dx * stepSize;
        y += dy * stepSize;

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
        // Implement logic to check if the rival is in a safe zone
        // Set isSafe to true if the rival reaches a safe zone
    }

    // Getter and setter for computer control
    public boolean isComputerControlled() {
        return isComputerControlled;
    }

    public void setComputerControlled(boolean isComputerControlled) {
        this.isComputerControlled = isComputerControlled;
    }
}
