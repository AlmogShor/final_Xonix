package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import utils.*;

public class HomeScreen extends JPanel implements ActionListener {
    private static JFrame frame;
    private JButton startButton;
    private JComboBox<String> sizeComboBox;
    private JComboBox<String> rivalComboBox;
    private JComboBox<String> monsterComboBox;

    public HomeScreen(JFrame frame) {
        HomeScreen.frame = frame;
        startButton = new JButton("Start Game");
        sizeComboBox = new JComboBox<>(new String[] { "Small", "Medium", "Large" });
        rivalComboBox = new JComboBox<>(new String[] { "Player", "AI" });
        monsterComboBox = new JComboBox<>(new String[] { "1", "2", "3", "4", "5" });

        add(startButton);
        add(sizeComboBox);
        add(rivalComboBox);
        add(monsterComboBox);

        startButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get selected options and start the game
        String size = (String) sizeComboBox.getSelectedItem();
        String rival = (String) rivalComboBox.getSelectedItem();
        int numMonsters = Integer.parseInt((String) monsterComboBox.getSelectedItem());
        if (size == "Small") {
            Constants.GRID_WIDTH = Constants.GRID_WIDTH_ORIGINAL;
            Constants.GRID_HEIGHT = Constants.GRID_HEIGHT_ORIGINAL;
            Constants.FRAME_HEIGHT = Constants.FRAME_HEIGHT_ORIGINAL;
            Constants.FRAME_WIDTH = Constants.FRAME_WIDTH_ORIGINAL;
        } else if (size == "Medium") {
            Constants.GRID_WIDTH = Constants.GRID_WIDTH_ORIGINAL + 10;
            Constants.GRID_HEIGHT = Constants.GRID_HEIGHT_ORIGINAL + 10;
            Constants.FRAME_HEIGHT = Constants.FRAME_HEIGHT_ORIGINAL + 100;
            Constants.FRAME_WIDTH = Constants.FRAME_WIDTH_ORIGINAL + 100;
        } else if (size == "Large") {
            Constants.GRID_WIDTH = Constants.GRID_WIDTH_ORIGINAL + 15;
            Constants.GRID_HEIGHT = Constants.GRID_HEIGHT_ORIGINAL + 15;
            Constants.FRAME_HEIGHT = Constants.FRAME_HEIGHT_ORIGINAL + 150;
            Constants.FRAME_WIDTH = Constants.FRAME_WIDTH_ORIGINAL + 150;
        }
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        GamePanel gamePanel = new GamePanel(
                new Player(null),
                new Rival(null),
                new CopyOnWriteArrayList<>());
        // set the monsters
        CopyOnWriteArrayList<monster> monsters = generateMonsters(gamePanel, numMonsters);
        gamePanel.setMonsters(monsters);

        // Update the player and rival with the gamePanel
        gamePanel.getPlayer().setGamePanel(gamePanel);
        gamePanel.getRival().setGamePanel(gamePanel);

        frame.remove(this);
        frame.add(gamePanel);
        frame.revalidate();
        // add the key listener
        frame.addKeyListener(gamePanel.getPlayer());
        frame.addKeyListener(gamePanel.getRival());

        // Start the game loop for player, rival, and monsters in separate threads
        // Start the game loop for updating the game state
        new Thread(new GameLoop(gamePanel, gamePanel::update)).start();

        // Start the game loop for player, rival, and monsters in separate threads
        new Thread(new GameLoop(gamePanel, () -> gamePanel.getPlayer().move())).start();
        new Thread(new GameLoop(gamePanel, () -> gamePanel.getRival().move())).start();
        new Thread(new GameLoop(gamePanel, () -> {

            for (Monster m : gamePanel.getMonsters()) {
                m.move();
                try {
                    Thread.sleep(100); // delay in milliseconds so it moves slower than player
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ex);
                }
            }
        })).start();

        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public static void deleteFrame() {
        if (frame == null) {
            return;
        }
        frame.dispose();
        frame = null;

    }

    private CopyOnWriteArrayList<Monster> generateMonsters(GamePanel gamePanel, int num) {
        // Generate monsters at random positions
        CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<>();
        for (int i = 0; i < num; i++) {
            // Generate a new monster in a random position
            int x = (int) (Math.random() * Constants.GRID_WIDTH);
            int y = (int) (Math.random() * Constants.GRID_HEIGHT);
            Monster monster = new Monster(gamePanel, x, y); // @TODO: Fix the frame to return the gamePanel
            monsters.add(monster);
        }
        return monsters;
    }
}
