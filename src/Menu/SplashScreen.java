package Menu;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This is the SplashScreen class that fades the company logo and game logo in and out for the game.
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 1.0, 2019-05-24
 * Created basic framework and functionality.
 *
 */
public class SplashScreen extends JPanel {
    /**
     * The images for our splash screen
     */
    private Image splash;
    private Image splash2;
    /**
     * The current shading gradient for our animation
     */
    private char animationShading;
    /**
     * Boolean to see whether in fading/increasing mode
     */
    private boolean isFading;
    /**
     * Counting the number of times the first animation has been shown
     */
    private volatile int counter;

    /**
     * Default constructor for the SplashScreen class.
     * Sets all default variable values.
     */
    public SplashScreen() {
        animationShading = 0;
        isFading = false;
        counter = 0;
        // Retrieves the images used for the company logo and game logo.
        splash = DefeatTheHeat.imageFromFile("Main/SplashScreen.png");
        splash2 = DefeatTheHeat.imageFromFile("Main/SplashScreen2.png");
    }

    /**
     * Overidden paintComponent method which is called when the method repaint() is called.
     *
     * @param g - the current Graphics we are trying to draw.
     */
    @Override
    public void paintComponent(Graphics g) {
        DefeatTheHeat.graphics = (Graphics2D) g;        // sets the updated Graphics to the main class' Graphics2D field

        if (isFading) animationShading--;       // if the animation is fading, reduce
        else animationShading++;                // if the animation is increasing, increase

        if (animationShading == 255) isFading = true; // if the gradient has reached peak, set the animation to fade
        if (animationShading == 0) goNext();       // if the animation has finished one cycle
        // Make proper call to which screen to display
        if (counter == 0) drawImage(splash, animationShading);
        else if (counter==1) drawImage(splash2, animationShading);
    }

    /**
     * Draws the passed in Image in the centre of the screen, with a black gradient.
     *
     * @param img   - The image to be drawn
     * @param alpha - The composite value to set
     */
    private void drawImage(Image img, char alpha) {
        // Sets the colour to a black colour (for the fade in/out effect)
        DefeatTheHeat.graphics.setColor(Color.BLACK);
        // Fills in the entire screen with a black rectangle
        DefeatTheHeat.graphics.fillRect(0, 0, getWidth(), getHeight());
        // Sets the composite value (to have different strengths of black, depending on the stage of the animation)
        DefeatTheHeat.graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255.f));
        // Draws at the centre pixel coordinate
        DefeatTheHeat.graphics.drawImage(img, (getWidth() - img.getWidth(null)) / 2, (getHeight() - img.getHeight(null)) / 2, null);
    }

    /**
     * Plays the splash screen animation. This method is public and is used in DefeatTheHeat main class.
     */
    public void play() {
        revalidate();
        // Starts the animation timer for the logo
        Timer timer = new Timer(5, this::actionPerformed);
        timer.start();
        while (counter == 0);   // continues this animation until one cycle of the COMPANY logo was displayed
        timer = new Timer(7, this::actionPerformed);    // new Timer for the GAME logo
        timer.start();
        while (counter == 1);
    }

    /**
     *  When an actionPerformed call is called inside the play() method, it updates the screen.
     *
     * @param e - The ActionEvent that was caught
     */
    private void actionPerformed(ActionEvent e) {
        repaint();
    }

    /**
     *  Moves to the next state of the animation (increases counter by 1).
     */
    private void goNext() {
        counter++;
        animationShading = 0;
        isFading = false;
    }
}
