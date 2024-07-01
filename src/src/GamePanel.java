package src;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import utils.*;

/**
 * The {@code GamePanel} class extends {@code JPanel} and is responsible for the graphical representation
 * of the game. It maintains and updates the positions and statuses of the game elements such as the player,
 * rival, and monsters. It also handles rendering of these elements and the game board.
 */
public class GamePanel extends JPanel {
    private Player player;  // The player object
    private Rival rival;    // The rival object
    private List<Monster> monsters;  // List of all active monsters in the game
    private int[][] occupied;  // Grid to keep track of which parts of the board are occupied
    private int gameOver;  // Status of the game: -1 = game not over, 0 = player lost, 1 = player won (no monsters left), 2 = area filled
    private boolean exitStatus = false;  // Exit status for the game loop

    /**
     * Constructs a {@code GamePanel} with specific game elements including player, rival, and monsters.
     * It initializes the game grid where game interactions occur.
     *
     * @param player The player object
     * @param rival The rival object
     * @param monsters The list of monsters in the game
     */
    public GamePanel(Player player, Rival rival, List<Monster> monsters) {
        this.player = player;
        this.rival = rival;
        this.monsters = monsters;
        this.gameOver = -1;
        this.occupied = new int[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];  // Initialize the game grid

        // Populate the grid boundaries as occupied
        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                if (i == 0 || j == 0 || i == Constants.GRID_WIDTH - 1 || j == Constants.GRID_HEIGHT - 1) {
                    occupied[i][j] = -1;
                } else {
                    occupied[i][j] = 0;
                }
            }
        }
    }

    /**
     * Overrides the {@code paintComponent} method to handle custom rendering of the game elements.
     * It calls methods to draw the board, player, rival, and monsters, and updates the displayed scores.
     *
     * @param g The {@code Graphics} object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);  // Draw the game board
        player.draw(g);  // Draw the player
        rival.draw(g);  // Draw the rival
        for (Monster monster : monsters) {
            monster.draw(g);  // Draw each monster
        }

        // Display scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + player.getScore(), 10, Constants.FRAME_HEIGHT - 50);
        g.drawString("Rival Score: " + rival.getScore(), 600, Constants.FRAME_HEIGHT - 50);
    }

    /**
     * Draws the game board. This method sets the background color and could potentially handle more
     * complex graphical elements related to the game board in the future.
     *
     * @param g The {@code Graphics} object used for drawing
     */
    private void drawBoard(Graphics g) {
        g.setColor(Color.GRAY);
        // Additional board drawing logic could be added here
    }
}
