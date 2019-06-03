package Game;

import Main.DefeatTheHeat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//version 2.0 May 24 addition, below imports added to use BufferedImage objects and read files to create the image
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

/**
 * This class creates the window and graphics for level one of Defeat the Heat. The player must find all the fire
 * hazards before moving to the next level.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Rohan Krishna
 * @version 2.0, May 27, 2019
 * <p>
 * <b> Time spent modifying file: </b> 2 hours
 * <p>
 * <b> Total time spent writing file: </b> 3 hours
 * <p>
 * <b> Modification summary: </b>
 * The image used for the background of level one is now stored as a BufferedImage. This allows the use of the getRGB()
 * method. A method for mouse interaction has been added which is executed whenever the mouse is pressed. A second
 * method has also been added that checks what object on the screen had been clicked.
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> image </b> A BufferedImage object storing the level one background.
 */
public class LevelOne {
    BufferedImage image; //version 2.0 May 25 addition, allows use of getRGB() to get colour of a specific pixel

    /**
     * This is the constructor for the LevelOne class.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> levelOnePic </b> The ImageIcon used as graphics for level one.
     * <p>
     * <b> label </b> Contains the level one image in a format that can be added to a JFrame.
     * <p>
     * <b> frame </b> The JFrame in which the graphics for level one are displayed.
     */
    public LevelOne() {

        image = DefeatTheHeat.imageFromFile("Game/LevelOne.png"); //version 2.0 May 24 addition, initializes BufferedImage
        ImageIcon levelOnePic = new ImageIcon(image);
        JLabel label = new JLabel(levelOnePic);
        JFrame frame = new JFrame("Level One");

        //version 2.0 May 24 addition, nested class used to implement user interaction with mouse input
        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                checkLocation(e.getX(), e.getY());
            }
        });
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    //version 2.0 May 26 addition, method used to determine whether a fire hazard was clicked by the user
    //if block contains print statements as temporary placeholders

    /**
     * This method checks if a fire hazard has been clicked.
     *
     * @param xCoord The x coordinate of the pixel that was clicked.
     * @param yCoord The y coordinate of the pixel that was clicked.
     *               <p>
     *               <b> Local Variables </b>
     *               <p>
     *               <b> pixelColour </b> Stores the colour of the clicked pixel as an int.
     */
    public void checkLocation(int xCoord, int yCoord) {
        int pixelColour = image.getRGB(xCoord, yCoord);

        if (xCoord >= 50 && xCoord <= 144 && yCoord >= 135 && yCoord <= 195
                && (pixelColour == Color.WHITE.getRGB() || pixelColour == -5592406))
            System.out.println("Clicked napkin");
        else if (xCoord >= 179 && xCoord <= 213 && yCoord >= 57 && yCoord <= 141)
            System.out.println("Clicked aerosol can");
        else if (xCoord >= 58 && xCoord <= 109 && yCoord >= 5 && yCoord <= 90 && pixelColour != -4984120)
            System.out.println("Clicked dusty vent");
        else if (xCoord >= 575 && xCoord <= 713 && yCoord >= 406 && yCoord <= 477 && pixelColour != -14)
            System.out.println("Clicked overloaded outlet");
        else if (xCoord >= 548 && xCoord <= 654 && yCoord >= 264 && yCoord <= 320 && pixelColour != -14)
            System.out.println("Clicked short circuit");
    }


    /**
     * Creates the LevelOne object and displays the window with graphics for level one.
     *
     * @param args String array that allows command line parameters to be used when executing the program.
     */
    public static void main(String args[]) {
        new LevelOne(); //version 2.0 May 23 modification, the LevelOne object is no longer assigned to a variable
    }
}