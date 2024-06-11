package src;

import java.awt.Graphics;
import java.awt.Color;
import utils.*;

public class monster {
    private int x, y;
    private gamePanel gamePanel;

    public monster(gamePanel gamePanel, int startX, int startY) {
        this.gamePanel = gamePanel;
        this.x = startX;
        this.y = startY;
    }

    public void move() {
        // Get the current positions of the player and rival
        int playerX = gamePanel.getPlayer().getX();
        int playerY = gamePanel.getPlayer().getY();
        int rivalX = gamePanel.getRival().getX();
        int rivalY = gamePanel.getRival().getY();

        // Calculate the distances to the player and rival
        double distanceToPlayer = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));
        double distanceToRival = Math.sqrt(Math.pow(rivalX - x, 2) + Math.pow(rivalY - y, 2));

        // Determine which one is closer
        boolean isPlayerCloser = distanceToPlayer < distanceToRival;

        // Calculate the new position of the monster
        int newX = x, newY = y;
        if (isPlayerCloser) {
            if (playerX > x) newX += 1;
            else if (playerX < x) newX -= 1;
            if (playerY > y) newY += 1;
            else if (playerY < y) newY -= 1;
        } else {
            if (rivalX > x) newX += 1;
            else if (rivalX < x) newX -= 1;
            if (rivalY > y) newY += 1;
            else if (rivalY < y) newY -= 1;
        }

        // Update the position of the monster
        x = newX;
        y = newY;
    }


    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
