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

        gamePanel gamePanel = null;
        gamePanel = new gamePanel(
                new player(gamePanel),
                new rival(gamePanel), // for now, only real time rival is implemented and not an AI
                generateMonsters(numMonsters)
        );
        frame.remove(this);
        frame.add(gamePanel);
        frame.revalidate();
    }

    private List<monster> generateMonsters(int num) {
        // Generate monsters at random positions
        List<monster> monsters = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            // Generate a new monster in a random position
            int x = (int) (Math.random() * Constants.GRID_WIDTH);
            int y = (int) (Math.random() * Constants.GRID_HEIGHT);
//            monster monster = new monster(this.frame.getOwner(), x, y); // @TODO: Fix the frame to return the gamePanel
//            monsters.add(monster);
        }
        return monsters;
    }
}
