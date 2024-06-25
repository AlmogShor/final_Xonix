package src;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import utils.*;

public class Monster {
    private int x, y;
    private GamePanel gamePanel;

    public Monster(GamePanel gamePanel, int startX, int startY) {
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

        // Determine which one is closer and not in the safe zone
        boolean shouldChasePlayer = distanceToPlayer < distanceToRival && !gamePanel.getPlayer().isInSafeZone();
        boolean shouldChaseRival = !gamePanel.getRival().isInSafeZone() && !shouldChasePlayer;

        // Calculate the new position of the monster
        int newX = x, newY = y;
        if (shouldChasePlayer) {
            if (playerX > x)
                newX += 1;
            else if (playerX < x)
                newX -= 1;
            if (playerY > y)
                newY += 1;
            else if (playerY < y)
                newY -= 1;
        } else if (shouldChaseRival) {
            if (rivalX > x)
                newX += 1;
            else if (rivalX < x)
                newX -= 1;
            if (rivalY > y)
                newY += 1;
            else if (rivalY < y)
                newY -= 1;
        } else {
            // chase player
            if (playerX > x)
                newX += 1;
            else if (playerX < x)
                newX -= 1;
        }

        // Check if the new position is valid
        if (gamePanel.isOccupied(newX, newY)) {
            return;
        }

        // Update the position of the monster
        x = newX;
        y = newY;

        // Check if the monster has caught the player
        if (gamePanel.getPlayer().getPath().contains(new Point(x, y))) {
            gamePanel.getPlayer().clearPath();
        } else if (isPlayerCaught()) {
            gamePanel.getPlayer().setX(0);
            gamePanel.getPlayer().setY(0);
            gamePanel.setGameOver(0);
            gamePanel.getPlayer().clearPath();
        }
        // Check if monster has caught the rival
        if (gamePanel.getRival().getPath().contains(new Point(x, y))) {
            gamePanel.getRival().clearPath();
        } else if (isRivalCaught()) {
            gamePanel.getRival().setX(0);
            gamePanel.getRival().setY(0);
            gamePanel.setGameOver(1);
            gamePanel.getRival().clearPath();
        }
    }

    private boolean isPlayerCaught() {
        // 1px absolute difference
        return Math.abs(gamePanel.getPlayer().getX() - x) <= 1 && Math.abs(gamePanel.getPlayer().getY() - y) <= 1
                && !gamePanel.getPlayer().isInSafeZone();
    }

    private boolean isRivalCaught() {
        return Math.abs(gamePanel.getRival().getX() - x) <= 1 && Math.abs(gamePanel.getRival().getY() - y) <= 1
                && !gamePanel.getRival().isInSafeZone();

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
