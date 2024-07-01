package src;

import javax.swing.JFrame;

import utils.*;

/**
 * The {@code Game} class serves as the entry point for the Xonix game application.
 * It initializes the game environment, setting up the main window frame and starting the game.
 */
public class Game {

    /**
     * The main method is the entry point for any Java application.
     * In this case, it creates an instance of {@code Game} and starts the game.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    /**
     * Initializes and displays the main game window with all necessary settings and the home screen.
     * It sets the size of the frame and the default close operation, ensuring that the frame is visible.
     * This method also triggers the deletion of any existing frames before setting up a new one.
     */
    public void start() {
        JFrame frame = new JFrame("Xonix Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        HomeScreen.deleteFrame();  // Deletes the previous instance of the frame if exists.
        HomeScreen homeScreen = new HomeScreen(frame);  // Initializes the home screen within the frame.
        frame.add(homeScreen);  // Adds the home screen to the frame.

        frame.setVisible(true);  // Makes the frame visible to start the game.
    }

}
