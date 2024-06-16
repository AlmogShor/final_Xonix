package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import utils.*;

public class homeScreen extends JPanel implements ActionListener {
    private JFrame frame;
    private JButton startButton;
    private JComboBox<String> sizeComboBox;
    private JComboBox<String> rivalComboBox;
    private JComboBox<String> monsterComboBox;

    public homeScreen(JFrame frame) {
        this.frame = frame;
        startButton = new JButton("Start Game");
        sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        rivalComboBox = new JComboBox<>(new String[]{"Player", "AI"});
        monsterComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

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

        gamePanel gamePanel = new gamePanel(
                new player(null),
                new rival(null),
                new ArrayList<>() // Temporarily empty list of monsters
        );
        // set the monsters
        List<monster> monsters = generateMonsters(gamePanel, numMonsters);
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
        new Thread(new gameLoop(gamePanel, () -> gamePanel.getPlayer().move())).start();
        new Thread(new gameLoop(gamePanel, () -> gamePanel.getRival().move())).start();
        new Thread(new gameLoop(gamePanel, () -> {
            for (monster m : gamePanel.getMonsters()) {
                m.move();
            }
        })).start();

        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    private List<monster> generateMonsters(gamePanel gamePanel ,int num) {
        // Generate monsters at random positions
        List<monster> monsters = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // Generate a new monster in a random position
            int x = (int) (Math.random() * Constants.GRID_WIDTH);
            int y = (int) (Math.random() * Constants.GRID_HEIGHT);
            monster monster = new monster(gamePanel, x, y); // @TODO: Fix the frame to return the gamePanel
            monsters.add(monster);
        }
        return monsters;
    }
}
