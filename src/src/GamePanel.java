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

public class GamePanel extends JPanel {
    private Player player;
    private Rival rival;
    private List<Monster> monsters;
    private int[][] occupied;
    private int gameOver; // -1 = game not over, 0 =player lost, 1 = player won due to no monsters left,
    // 2 = player won due to area filled
    private boolean exitStatus = false; // Exit status for the game loop

    public GamePanel(Player player, Rival rival, List<Monster> monsters) {
        this.player = player;
        this.rival = rival;
        this.monsters = monsters;
        this.gameOver = -1;
        this.occupied = new int[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];
        System.out.println("width and height: " + Constants.GRID_WIDTH + " " + Constants.GRID_HEIGHT);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        player.draw(g);
        rival.draw(g);
        for (Monster monster : monsters) {
            monster.draw(g);
        }

        // Draw player score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + player.getScore(), 10, Constants.FRAME_HEIGHT - 50);
        g.drawString("Rival Score: " + rival.getScore(), 600, Constants.FRAME_HEIGHT - 50);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Draw occupied areas
        for (int x = 0; x < Constants.GRID_WIDTH; x++) {
            for (int y = 0; y < Constants.GRID_HEIGHT; y++) {
                if (occupied[x][y] == 1) {
                    g.setColor(Constants.PLAYER_TRAIL_COLOR);
                    g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE,
                            Constants.CELL_SIZE);
                } else if (occupied[x][y] == 2) {
                    g.setColor(Constants.RIVAL_TRAIL_COLOR);
                    g.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, Constants.CELL_SIZE,
                            Constants.CELL_SIZE);
                }
            }
        }

        int boardWidth = Constants.GRID_WIDTH * Constants.CELL_SIZE;
        int boardHeight = Constants.GRID_HEIGHT * Constants.CELL_SIZE;

        int borderSize = 10; // Size of the border in pixels
        g.setColor(Color.PINK); // Set the color for the border
        g.fillRect(0, 0, boardWidth, borderSize); // Top border
        g.fillRect(0, boardHeight - borderSize, boardWidth, borderSize); // Bottom border
        g.fillRect(0, 0, borderSize, boardHeight); // Left border
        g.fillRect(boardWidth - borderSize, 0, borderSize, boardHeight); // Right border
    }

    public int fillSmallerEnclosedArea(int player) {
        int oldOccupiedCount = 0;
        int borderCOunt = 0;
        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                if (occupied[i][j] != 0 && occupied[i][j] != -1) {
                    oldOccupiedCount++;
                }
                if (occupied[i][j] == -1) {
                    borderCOunt++;
                }
            }
        }

        int[][] copy = new int[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];
        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            System.arraycopy(occupied[i], 0, copy[i], 0, Constants.GRID_HEIGHT);
        }
        int unOccupiedX = -1, unOccupiedY = -1;
        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                if (copy[i][j] == 0) {
                    unOccupiedX = i;
                    unOccupiedY = j;
                    break;
                }
            }
            if (unOccupiedX != -1) {
                break;
            }
        }

        floodFill(copy, unOccupiedX, unOccupiedY);

        int enclosedAreaSize = 0;
        int unOccupiedArea = 0;
        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                if (copy[i][j] == 3) {
                    enclosedAreaSize++;
                } else if (copy[i][j] == 0) {
                    unOccupiedArea++;
                }
            }
        }

        if (enclosedAreaSize > unOccupiedArea) {
            for (int i = 0; i < Constants.GRID_WIDTH; i++) {
                for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                    if (copy[i][j] == 0) {
                        occupied[i][j] = player;
                    }
                }
            }
            System.out.println("Border Count in if case: " + borderCOunt);
            System.out.println("Old Occupied Count: " + oldOccupiedCount);
            System.out.println("Enclosed Area Size: " + enclosedAreaSize);
            System.out.println("Points: " + (enclosedAreaSize - oldOccupiedCount));
            return unOccupiedArea - oldOccupiedCount > 0 ? unOccupiedArea - oldOccupiedCount : 0;
        } else {
            for (int i = 0; i < Constants.GRID_WIDTH; i++) {
                for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                    if (copy[i][j] == 3) {
                        occupied[i][j] = player;
                    }
                }
            }
            System.out.println("Border Count: " + borderCOunt);
            System.out.println("Old Occupied Count: " + oldOccupiedCount);
            System.out.println("Enclosed Area Size: " + enclosedAreaSize);
            System.out.println("Points: " + (enclosedAreaSize - oldOccupiedCount));
            return enclosedAreaSize - oldOccupiedCount > 0 ? enclosedAreaSize - oldOccupiedCount : 0;
        }
    }

    private void floodFill(int[][] array, int x, int y) {
        if (x < 0 || x >= Constants.GRID_WIDTH || y < 0 || y >= Constants.GRID_HEIGHT || array[x][y] != 0) {
            return;
        }

        array[x][y] = 3;

        floodFill(array, x + 1, y);
        floodFill(array, x - 1, y);
        floodFill(array, x, y + 1);
        floodFill(array, x, y - 1);
    }

    public boolean getExitStatus() {
        return exitStatus;
    }

    public void update() {

        if (player.isInSafeZone()) {
            if (!player.getPath().isEmpty()) {

                // Fill area defined by player's path
                for (Point p : player.getPath()) {
                    occupied[p.x][p.y] = 1;
                }
                int points = fillSmallerEnclosedArea(1) + player.getPath().size(); // 1 point for each cell filled
                int monstersCaught = checkCaughtMonsters();
                // 10% extra points for each monster caught
                points += points * monstersCaught * 0.1;
                player.setScore(player.getScore() + points);
                System.out.println("Player Score: " + player.getScore());
                player.clearPath();
            }
        }

        if (rival.isInSafeZone()) {
            if (!rival.getPath().isEmpty()) {

                // Fill area defined by rival's path
                for (Point p : rival.getPath()) {
                    occupied[p.x][p.y] = 2;
                }
                int points = fillSmallerEnclosedArea(2) + rival.getPath().size(); // 1 point for each cell filled
                int monstersCaught = checkCaughtMonsters();
                // 10% extra points for each monster caught
                points += points * monstersCaught * 0.1;
                rival.setScore(rival.getScore() + points);
                System.out.println("Rival Score: " + rival.getScore());
                rival.clearPath();
            }
        }

        if (monsters.isEmpty() || getOccupiedAreaPercentage() >= Constants.WINNING_AREA_PERCENTAGE) {
            gameOver = 1;
            endGame();
        }
    }

    public void endGame() {
        new Thread(() -> {
            String message = "";
            int score = 0;
            score = player.getScore() > rival.getScore() ? player.getScore() : rival.getScore();
            boolean player1Win = player.getScore() > rival.getScore();
            boolean isDraw = player.getScore() == rival.getScore();
            if (player1Win && !isDraw) {
                message = "Player won the game with a score of " + score;
            } else if (!player1Win && !isDraw) {
                message = "Rival won the game with a score of " + score;
            } else {
                message = "Game is draw with a score of " + score;
            }
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            int maxScore = player.getScore() > rival.getScore() ? player.getScore() : rival.getScore();
            String msg = maxScore == player.getScore() ? "Player had the highest score: " + player.getScore()
                    : "Rival had the highest score: " + rival.getScore();
            String username = JOptionPane.showInputDialog(this, msg + " .Enter your username to save your score:");
            if (username != null && !username.isEmpty()) {
                saveScore(username, maxScore);
            }
            // start game again
            exitStatus = true;
            Game game = new Game();
            game.start();

        }).start();

    }

    private void saveScore(String username, int score) {
        GameLogger.logResult(username + "," + score);

    }

    private double getOccupiedAreaPercentage() {
        int totalCells = Constants.GRID_WIDTH * Constants.GRID_HEIGHT;
        int occupiedCells = 0;

        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                if (occupied[i][j] != 0) {
                    occupiedCells++;
                }
            }
        }

        return (double) occupiedCells / totalCells * 100;

    }

    private int checkCaughtMonsters() {
        List<Monster> monstersToRemove = new ArrayList<>();
        for (Monster monster : monsters) {
            if (occupied[monster.getX()][monster.getY()] != 0) {
                monstersToRemove.add(monster);
            }
        }
        monsters.removeAll(monstersToRemove);
        return monstersToRemove.size();
    }

    public boolean isOccupied(int x, int y) {
        // Check if the cell at the given coordinates is occupied
        return occupied[x][y] != 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGameOver(int gameOver) {
        this.gameOver = gameOver;
        endGame();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Rival getRival() {
        return rival;
    }

    public void setRival(Rival rival) {
        this.rival = rival;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public int getGameOver() {
        return gameOver;
    }

}
