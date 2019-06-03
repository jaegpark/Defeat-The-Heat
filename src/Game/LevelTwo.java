package Game;

import Main.DefeatTheHeat;

import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * This class creates the window and graphics for level two of Defeat the Heat. The player must put out all fires with
 * the appropriate equipment before moving on to the next level.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Rohan Krishna
 * @version 2.0, May 27, 2019
 * <p>
 * <b> Total time spent writing file: </b> 0.5 hours
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> image </b> A BufferedImage object storing the level two background.
 */
public class LevelTwo {
    BufferedImage image;

    /**
     * This is the constructor for the LevelTwo class.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> levelTwoPic </b> The ImageIcon used as graphics for level two.
     * <p>
     * <b> label </b> Contains the level two image in a format that can be added to a JFrame.
     * <p>
     * <b> frame </b> The JFrame in which the graphics for level two are displayed.
     */
    public LevelTwo() {

        image = DefeatTheHeat.imageFromFile("Game/LevelTwo.png");
        ImageIcon levelTwoPic = new ImageIcon(image);
        JLabel label = new JLabel(levelTwoPic);
        JFrame frame = new JFrame("Level Two");

        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

            }
        });
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    /**
     * Creates the LevelTwo object and displays the window with graphics for level two.
     *
     * @param args String array that allows command line parameters to be used when executing the program.
     */
    public static void main(String args[]) {
        new LevelTwo();
    }
}