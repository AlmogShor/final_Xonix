package src;

import javax.swing.JFrame;

import utils.*;

public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void start() {
        JFrame frame = new JFrame("Xonix Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        HomeScreen.deleteFrame();
        HomeScreen homeScreen = new HomeScreen(frame);
        frame.add(homeScreen);

        frame.setVisible(true);
    }

}
