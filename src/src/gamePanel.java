package src;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.awt.Point;

import utils.*;

public class gamePanel extends JPanel {
    private player player;
    private rival rival;
    private List<monster> monsters;
    private boolean[][] occupied;

    public gamePanel(player player, rival rival, List<monster> monsters) {
        this.player = player;
        this.rival = rival;
        this.monsters = monsters;
        this.occupied = new boolean[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        player.draw(g);
        rival.draw(g);
        for (monster monster : monsters) {
            monster.draw(g);
        }
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Draw occupied areas
        g.setColor(Color.GREEN);
        for (int x = 0; x < Constants.GRID_WIDTH; x++) {
            for (int y = 0; y < Constants.GRID_HEIGHT; y++) {
                if (occupied[x][y]) {
                    g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
                }
            }
        }
    }

    // Other methods for updating game state, handling collisions, etc.
    public void fillArea(List<Point> path, Color color) {
        // Convert the path to a 2D boolean array
        boolean[][] area = new boolean[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];
        for (Point point : path) {
            area[point.x][point.y] = true;
        }

        // Fill the enclosed area with the specified color
        for (int x = 0; x < Constants.GRID_WIDTH; x++) {
            for (int y = 0; y < Constants.GRID_HEIGHT; y++) {
                if (area[x][y]) {
//                    g.setColor(color);
//                    g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
//                    occupied[x][y] = true;
                    /**
                     * @TODO: Fill the enclosed area with the specified color
                     */
                }
            }
        }
    }
    public boolean isOccupied(int x, int y) {
        // Check if the cell at the given coordinates is occupied
        return occupied[x][y];
    }

    public player getPlayer() {
        return player;
    }

    public void setPlayer(player player) {
        this.player = player;
    }

    public rival getRival() {
        return rival;
    }

    public void setRival(rival rival) {
        this.rival = rival;
    }

    public List<monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<monster> monsters) {
    this.monsters = monsters;
    }
}

