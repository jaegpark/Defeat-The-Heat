package Game; //version 4.0 June 8 addition, allows this class to be used from the driver class

import Main.DefeatTheHeat; //version 4.0 June 8 addition, allows effective file reading and exception handling

import java.awt.event.*;
import javax.swing.*;
//version 4.0 June 4 modification, java.awt.Color import removed as it is unused
import java.awt.image.BufferedImage;
//version 4.0 June 4 addition, allows use of ArrayList and the Arrays.asList() method
import java.util.ArrayList;
import java.util.Arrays;
//version 4.0 June 4 addition, allows use of Timer and TimerTask classes to schedule a task
import java.util.Timer;
import java.util.TimerTask;
//version 4.0 June 5 addition, allows use of Color class to set the colour of the border for JDialogs
import java.awt.Color;
//version 4.0 June 5 addition, allows use of Font in JDialogs
import java.awt.Font;
//version 4.0 June 4 addition, allows use of FlowLayout in JDialogs
import java.awt.FlowLayout;

/**
 * This class creates the window and graphics for level two of Defeat the Heat. The player must put out all fires with
 * the appropriate equipment before moving on to the next level.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Rohan Krishna
 * @version 4.0 June 10, 2019
 * <p>
 * <b> Time spent modifying file: </b> 1 hours
 * <p>
 * <b> Total time spent writing file: </b> 8 hours
 * <p>
 * <b> Modification summary: </b>
 * The JLabels representing equipment can now be dragged around. Fires appear on the screen at four different locations
 * and dragging the correct JLabel over a fire will put it out. JDialogs are displayed upon winning or losing the game.
 * An instructions window is displayed at the beginning of the level. The constructor has been modified to allow all
 * level two graphics to be displayed on one JFrame shared between levels. The main method has been removed.
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> image </b> A BufferedImage object storing the level two background.
 * <p>
 * <b> label </b> The JLabel storing the image for level two.
 * <p>
 * <b> frame </b> The JFrame on which level two is displayed.
 * <p>
 * <b> potLid </b> The JLabel containing the transparent image of a pot lid.
 * <p>
 * <b> bucket </b> The JLabel containing the transparent image of a bucket.
 * <p>
 * <b> blanket </b> The JLabel containing the transparent image of a blanket.
 * <p>
 * <b> bakingSoda </b> The JLabel containing the transparent image of a baking soda box.
 * <p>
 * <b> startX </b> Stores the x coordinate where the mouse was clicked prior to dragging it.
 * <p>
 * <b> startY </b> Stores the y coordinate where the mouse was clicked prior to dragging it.
 * <p>
 * <b> endDialog </b> Stores the JDialog that is shown when the level is won or lost.
 * <p>
 * <b> rulesDialog </b> Stores the JDialog that shows the rules for level two.
 */
public class LevelTwo {
    private BufferedImage image;
    private JLabel label; //version 4.0 June 9 modification, made instance level to be used by set-up methods in constructor
    private JFrame frame; //version 4.0 June 5 modification, made instance level so it can be set as the parent for the win/lose JDialog.
    private JLabel potLid;
    private JLabel bucket;
    private JLabel blanket;
    private JLabel bakingSoda;
    //version 4.0 June 3 additions, store information to be passed to the DragAndDrop object
    private int startX;
    private int startY;
    //version 4.0 June 6 addition, made instance level so it can be manipulated in the frame's componentMoved() method
    private JDialog endDialog;
    private JDialog rulesDialog; //version 4.0 June 8 addition, stores the JDialog that displays the rules
    private boolean rulesClosed; //version 4.0 June 8 addition, indicates whether instructions have been closed
    public boolean checkGame;   // version 4.0 June 9 addition, indicates whether the game was won or not

    /**
     * This is the constructor creates the JFrame and add all images onto it after initializes all JLabels.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> levelTwoPic </b> The ImageIcon used as graphics for level two.
     * <p>
     * <b> label </b> Contains the level two image in a format that can be added to a JFrame.
     * <p>
     * <b> items </b> An ArrayList storing the JLabels used to display the items used to put out fire.
     * <p>
     * <b> t </b> The Time object that allows a TimerTask to be scheduled.
     * <p>
     * <b> task </b> The TimerTask object that is scheduled to repeat to create the fires.
     * <p>
     * <b> d </b> The DragAndDrop object that allows JLabels to be dragged and moved.
     * <p>
     * <b> itemPressed </b> Stores the JLabel that is clicked on.
     *
     * @param theFrame - THe JFrame that is being pointed to for all screen manipulation.
     */
    public LevelTwo(JFrame theFrame) //version 4.0 June 8 modification, avoids creating a new window
    {
        image = DefeatTheHeat.imageFromFile("Game/LevelTwo.png");
        label = new JLabel(new ImageIcon(image));
        frame = theFrame;
        rulesClosed = false; //version 4.0 June 8 addition, indicates that the user has not yet closed the instructions
        potLid = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/pot lid.png")));
        bucket = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/bucket.png")));
        blanket = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/blanket.png")));
        bakingSoda = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/baking soda.png")));
        //version 4.0 June 4 addition, ArrayList needed to pass information to the DragAndDrop and RandomFires constructors
        ArrayList<JLabel> items = new ArrayList<JLabel>(Arrays.asList(potLid, bucket, blanket, bakingSoda));

        setUpFrame(); //version 4.0 June 9 modification, window set up moved to another method for organization
        displayRules(); //version 4.0 June 8 addition, displays the instructions JDialog
        while (!rulesClosed) //version 4.0 June 8 addition, starts gameplay only after rules are closed
        {
            System.out.print("");
        }
        //version 4.0 June 4 addition, creates random display of fires on the JFrame
        Timer t = new Timer();
        TimerTask task = new RandomFires(frame, label, items);
        t.schedule(task, 1000, 500);
        //version 4.0 June 3 addition, allows use of drag and drop
        DragAndDrop d = new DragAndDrop(frame, items);
        d.start(); //starts the Thread
        setClickProcessing(d, task); //version 4.0 June 9 addition, sets up mouse listener to use with the DragAndDrop and RandomFires objects

        //version 4.0 June 5 addition, calls the method to open the win/lose JDialog depending on information from the RandomFires object
        if (((RandomFires) task).alertWhenFinished().equals("win")) {
            levelComplete();
            checkGame = true;
        } else {
            checkGame = false;
            gameOver();
        }
    }


    //version 4.0 June 9 addition, window set up moved to this method for organization

    /**
     * This method sets up the JFrame and adds the JLabel to be used as the background image. A component listener is
     * also added to the JFrame.
     */
    private void setUpFrame() {
        //window set up
        label.setSize(800, 565);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLayout(null);
        potLid.setSize(200, 70);
        potLid.setLocation(10, 460);
        bucket.setSize(100, 110);
        bucket.setLocation(245, 440);
        blanket.setSize(145, 100);
        blanket.setLocation(400, 445);
        bakingSoda.setSize(80, 100);
        bakingSoda.setLocation(590, 445);
        frame.add(potLid);
        frame.add(bucket);
        frame.add(blanket);
        frame.add(bakingSoda);

        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //version 4.0 June 6 addition, sets the win/lose JDialog centered in the parent even when it is moved
        frame.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                if (rulesDialog != null)
                    rulesDialog.setLocationRelativeTo(frame);
                if (endDialog != null)
                    endDialog.setLocationRelativeTo(frame);
            }
        });
    }


    /**
     * This method TODO: HERE
     *
     * @param dr
     * @param theTask
     */
    private void setClickProcessing(DragAndDrop dr, TimerTask theTask) {
        label.addMouseListener(new MouseAdapter() {
            JLabel itemPressed; //version 4.0 June 3 addition, stores the last JLabel that was clicked

            public void mousePressed(MouseEvent e) {
                //version 4.0 June 3 additions, sends information to the DragAndDrop object
                startX = e.getX();
                startY = e.getY();

                itemPressed = selectItem(startX, startY);
                if (itemPressed != null) {
                    dr.pressMouse(startX, startY, itemPressed);
                }
            }

            //version 4.0 June 3 addition, notifies the DragAndDrop object when the mouse is released
            public void mouseReleased(MouseEvent e) {
                dr.releaseMouse();
                //version 4.0 June 5 addition, sends the JLabel object to the RandomFires class after an item is placed on the screen
                if (itemPressed != null)
                    ((RandomFires) theTask).eraseFire(e.getX(), e.getY(), itemPressed);
            }
        });
    }


    //version 4.0 June 8 addition, displays the rules for level two

    /**
     * This method displays the rules for level two in a JDialog.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> rulesLabel </b> The JLabel explaining the rules for level two.
     */
    public void displayRules() {
        rulesDialog = new JDialog(frame, "Instructions");
        JLabel rulesLabel = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/Instructions 2.png")));

        rulesLabel.setVerticalAlignment(JLabel.CENTER);
        rulesLabel.setHorizontalAlignment(JLabel.CENTER);
        rulesDialog.setSize(500, 560);
        rulesDialog.setLocationRelativeTo(frame);
        rulesDialog.add(rulesLabel);

        rulesDialog.addWindowListener(new WindowAdapter() //version 4.0 June 8 addition, allows gameplay to start once instructions are closed
        {
            public void windowClosing(WindowEvent e) {
                rulesDialog = null;
                rulesClosed = true;
            }
        });

        rulesDialog.setVisible(true);
        rulesDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }


    //version 4.0 June 3 addition, returns the JLabel that is clicked on

    /**
     * This method identifies and returns the JLabel that is clicked on.
     *
     * @param xCoord The x coordinate where the mouse was clicked.
     * @param yCoord The y coordinate where the mosue was clicked.
     * @return The JLabel that is clicked on.
     */
    public JLabel selectItem(int xCoord, int yCoord) {
        if (xCoord > 20 && xCoord < 191 && yCoord > 464 && yCoord < 516)
            return potLid;
        else if (xCoord > 246 && xCoord < 342 && yCoord > 438 && yCoord < 550)
            return bucket;
        else if (xCoord > 398 && xCoord < 546 && yCoord > 442 && yCoord < 546)
            return blanket;
        else if (xCoord > 588 && xCoord < 670 && yCoord > 446 && yCoord < 546)
            return bakingSoda;
        return null;
    }


    //version 4.0 June 5 addition, opens a window when 15 fires have been put out

    /**
     * This method opens a window upon 15 hazards being extinguished.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> winDialog </b> The window that is opened when the level is won.
     * <p>
     * <b> heading </b> The JLabel used as the heading for the JDialog.
     */
    public void levelComplete() {
        JDialog winDialog = new JDialog(frame);
        JLabel heading = new JLabel();
        endDialog = winDialog;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        heading.setFont(new Font("Serif", Font.BOLD, 40));
        heading.setText("Level Complete!");
        winDialog.setSize(510, 400);
        winDialog.setLayout(null);
        winDialog.setUndecorated(true);
        winDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        winDialog.getContentPane().setBackground(new Color(128, 210, 145));
        winDialog.add(heading);
        heading.setLocation(120, 30);
        heading.setSize(400, 50);
        winDialog.setResizable(false);
        winDialog.setLocationRelativeTo(frame);
        winDialog.setVisible(true);
    }


    //version 4.0 June 5 addition, opens a window when all 4 fires burn at the same time

    /**
     * This method opens a window upon the time running out.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> loseDialog </b> The window that is opened when time runs out.
     * <p>
     * <b> heading </b> The JLabel used as the heading for the JDialog.
     */
    public void gameOver() {
        JDialog loseDialog = new JDialog(frame);
        JLabel heading = new JLabel();
        endDialog = loseDialog;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        heading.setFont(new Font("Serif", Font.BOLD, 40));
        heading.setText("You Lose");
        loseDialog.setSize(510, 400);
        loseDialog.setLayout(null);
        loseDialog.setUndecorated(true);
        loseDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        loseDialog.getContentPane().setBackground(new Color(248, 131, 134));
        loseDialog.add(heading);
        heading.setLocation(170, 30);
        heading.setSize(400, 50);
        loseDialog.setResizable(false);
        loseDialog.setLocationRelativeTo(frame);
        loseDialog.setVisible(true);
    }


    //version 4.0 June 9 addition, allows the driver class to clear the frame before adding the next level

    /**
     * This method erases all level two components from the JFrame when the level ends.
     *
     * @return - returns the JDialog that is on the screen.
     */
    public JDialog getEndDialog() {
        while (endDialog == null) {
        }
        return endDialog;
    }


    //version 4.0 June 9 addition, allows the driver class to clear the frame before adding the next level

    /**
     * This method erases all level two components from the JFrame when the level ends.
     */
    public void eraseComponents() {
        frame.getContentPane().removeAll();
        endDialog.dispose();
    }
    //version 4.0 June 8 modification, main method removed as a separate driver class is now used
}