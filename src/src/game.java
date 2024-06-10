package src;

import javax.swing.JFrame;
import utils.*;

public class game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Xonix Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        
        homeScreen homeScreen = new homeScreen(frame);
        frame.add(homeScreen);
        
        frame.setVisible(true);
    }
}
