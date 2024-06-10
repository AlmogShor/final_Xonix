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
        // Simple algorithm to move towards the nearest player
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }
}
