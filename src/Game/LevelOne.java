package Game; //version 4.0 June 8 addition, allows this class to be used from the driver class

import Main.DefeatTheHeat; //version 4.0 June 8 addition, allows effective file reading and exception handling
import Menu.ContinueMenu; //version 4.0 June 9 addition, allows use of the continue game option

import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.FlowLayout; 
import java.awt.Font; 
import java.util.*;

/**
 * This class creates the window and graphics for level one of Defeat the Heat. The player must find all the fire
 * hazards before moving to the next level.
 * 
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 * 
 * @author Rohan Krishna
 * @version 4.0 June 10, 2019
 * <p>
 * <b> Time spent modifying file: </b> 0.5 hours
 * <p>
 * <b> Total time spent writing file: </b> 11.5 hours
 * <p>
 * <b> Modification summary: </b>
 * An instructions window is now opened at the beginning of the level. The instructions window and win/lose window
 * are now fixed to the JFrame even upon moving the JFrame. The constructor has been altered to allow all level one
 * graphics to be displayed on a single JFrame shared between levels. The main method has been removed.
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> frame </b> The JFrame on which the Level One graphics are displayed
 * <p>
 * <b> label </b> The JLabel storing the image for the Level One background.
 * <p>
 * <b> image </b> A BufferedImage object storing the level one background.
 * <p>
 * <b> t </b> The TimeDisplay object that is created to add a timer onto the window.
 * <p>
 * <b> rulesDialog </b> The JDialog displaying the rules of level one.
 * <p>
 * <b> infoDialog </b> The JDialog that pops up upon a hazard being clicked. Only one is active at a time.
 * <p>
 * <b> endDialog </b> Stores the JDialog that is shown when the game is won or lost.
 * <p>
 * <b> hazardNames </b> A List that stores the names of the hazards.
 * <p>
 * <b> hazardsFound </b> A boolean array whose indexes correspond with those of hazardNames. Indicates which hazards are found.
 * <p>
 * <b> allHazardsFound </b> A boolean variable indicating whether or not all hazards have been found by the player.
 * <p>
 * <b> rulesClosed </b> Indicates whether the instructions have been closed and the game can begin.
 */
public class LevelOne
{
    private JFrame frame; 
    private JLabel label; //version 4.0 June 9 addition, made instance level to be used in other methods for more organized code
    private BufferedImage image;
    private TimeDisplay t; 
    private JDialog rulesDialog; //version 4.0 June 4 addition, creates a JDialog explaining the rules of level one
    private JDialog infoDialog;
    private JDialog endDialog; //version 4.0 June 4 addition, allows the win/lose JDialog to be relocated in the componentMoved() method
    private List<String> hazardNames; 
    private boolean[] hazardsFound; 
    private boolean allHazardsFound; 
    private boolean rulesClosed; //version 4.0 June 8 addition, indicates whether instructions have been closed
    
    /**
     * This is the constructor for the LevelOne class.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> levelOnePic </b> The ImageIcon used as graphics for level one.
     * <p>
     * <b> label </b> Contains the level one image in a format that can be added to a JFrame.
     *
     * @param theFrame The JFrame on which the graphics are displayed.
     */
    public LevelOne (JFrame theFrame) //version 4.0 June 8 modification, avoids creating a new window
    {
        hazardNames = new ArrayList<String>(Arrays.asList ("napkin", "aerosol can", "dusty vent", "overloaded outlet", "short circuit"));
        hazardsFound = new boolean[hazardNames.size ()];
        allHazardsFound = false;
        rulesClosed = false; //version 4.0 June 8 addition, indicates that the user has not yet closed the instructions window
        
        //create a window with an image and a timer
        image = DefeatTheHeat.imageFromFile("Game/LevelOne.png");
        ImageIcon levelOnePic = new ImageIcon (image);
        label = new JLabel (levelOnePic); //version 4.0 June 9 modification, made instance level to be used by set-up methods in constructor
        frame = theFrame;
        t = new TimeDisplay (frame, 50, 480, 120);
        
        setClickProcessing(); //version 4.0 June 9 modification, calls this method instead to add a mouse listener to the JLabel
        setUpFrame (); //version 4.0 June 9 modification, window set up moved to another method for organization
        displayRules (); //version 4.0 June 7 addition, displays the instructions JDialog
        startTimer (); //version 4.0 June 9 addition, starts the timer when instructions are closed
    }

    
    //version 4.0 June 9 addition, window set up moved to this method for organization
    /**
     * This method sets up the JFrame and adds the JLabel to be used as the background image. A component listener is
     * also added to the JFrame.
     */ 
    private void setUpFrame ()
    {
        //window set up
        label.setSize (800, 565); 
        frame.setSize (800, 600);
        frame.setLayout (null); 
        frame.setResizable (false);
        frame.add (label);
        frame.setVisible (true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        //version 4.0 June 8 addition, sets the win/lose JDialog and instructions JDialog centered in the parent even when it is moved
        frame.addComponentListener(new ComponentAdapter()
        {
            public void componentMoved (ComponentEvent e)
            {
                if (rulesDialog != null)
                    rulesDialog.setLocationRelativeTo (frame);
                if (endDialog != null) {
                    endDialog.setLocationRelativeTo(frame);
                }
            }
        });
    }
    
    
    //version 4.0 June 9 addition, mouse listener code moved to this method to simplify the constructor
    /**
     * Adds a mouse listener to the background image to check whether to open a hazard JDialog or to resume the timer.
     */ 
    private void setClickProcessing ()
    {
        label.addMouseListener(new MouseAdapter ()
        {                
            public void mousePressed (MouseEvent e)
            {
                if (rulesDialog != null) //version 4.0 June 8 addition, disables hazard windows while the instructions are active
                    return;
                if (infoDialog != null) 
                {
                    infoDialog.dispose ();
                    infoDialog = null;
                    //check if all hazards have been discovered
                    allHazardsFound = true;
                    for (int x = 0; x < hazardsFound.length; x++)
                    {
                        if (hazardsFound[x] == false)
                        {
                            allHazardsFound = false;
                        }
                    }
                    //open level completion window after short delay
                    if (allHazardsFound)
                    {
                        try 
                        {
                            Thread.sleep (700);
                        }
                        catch (InterruptedException ie)
                        {
                        }
                        levelComplete ();
                        t.endTimer (); //version 4.0 June 9 addition, sets the timer to 0 upon winning the level
                    }
                    else //only resumes timer if hazards remain undiscovered
                    {
                        t.resumeTimer (); //version 4.0 June 7 modification, no longer uses resume() as it is deprecated
                    }
                }
                if (!allHazardsFound && !t.isFinished ()) 
                    checkLocation (e.getX (), e.getY ());
            }
        });
    }
    
    
    //version 4.0 June 8 addition, displays the rules for level one
    /**
     * This method displays the rules for level one in a JDialog.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> rulesLabel </b> The JLabel with explaining the rules for level one.
     */ 
    private void displayRules ()
    {
        rulesDialog = new JDialog (frame, "Instructions");
        JLabel rulesLabel = new JLabel (new ImageIcon (DefeatTheHeat.class.getClassLoader().getResource("Game/Instructions 1.png")));
        
        rulesLabel.setVerticalAlignment (JLabel.CENTER);
        rulesLabel.setHorizontalAlignment (JLabel.CENTER);
        rulesDialog.setSize (500, 320);
        rulesDialog.setLocationRelativeTo (frame);
        rulesDialog.add (rulesLabel);
        
        rulesDialog.addWindowListener(new WindowAdapter() //version 4.0 June 8 addition, allows timer to start once instructions close
        {
            public void windowClosing(WindowEvent e)
            {
                rulesDialog = null;
                rulesClosed = true;
            }
        });
        
        rulesDialog.setVisible (true);
        rulesDialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
    }
    
    
    //version 4.0 June 9 modification, code moved to separate method from constructor for organization
    /**
     * This method starts the 2 minute timer and displays the "You Lose" JDialog when it hits 0.
     */ 
    private void startTimer ()
    {
        while (!rulesClosed) //version 4.0 June 8 addition, starts the timer only after rules are closed
        {
            System.out.print (""); //forces the loop to use the updated value of rulesClosed
        }
        t.start (); 
        t.waitUntilFinish ();
        if (endDialog == null) //version 4.0 June 9 addition, prevents creating two end windows when the game is won and timer set to 0
        {
            try
            {
                Thread.sleep (700);
            }
            catch (InterruptedException ie)
            {
            }
            gameOver ();
        }
    }
    

    /**
     * This method checks if a fire hazard has been clicked.
     * 
     * @param xCoord The x coordinate of the pixel that was clicked.
     * @param yCoord The y coordinate of the pixel that was clicked.
     * <p>
     * <b> Local Variables </b> 
     * <p>
     * <b> pixelColour </b> Stores the colour of the clicked pixel as an int.
     */ 
    private void checkLocation (int xCoord, int yCoord)
    {
        int pixelColour = image.getRGB (xCoord, yCoord);

        if (xCoord >= 50 && xCoord <= 144 && yCoord >= 135 && yCoord <= 195
                && (pixelColour == Color.WHITE.getRGB() || pixelColour == -5592406))
           hazardClicked ("napkin");
        else if (xCoord >= 179 && xCoord <= 213 && yCoord >= 57 && yCoord <= 141)
            hazardClicked ("aerosol can");
        else if (xCoord >= 58 && xCoord <= 109 && yCoord >= 5 && yCoord <= 90 && pixelColour != -4984120)
            hazardClicked ("dusty vent");
        else if (xCoord >= 575 && xCoord <= 713 && yCoord >= 406 && yCoord <= 477 && pixelColour != -14)
            hazardClicked ("overloaded outlet");
        else if (xCoord >= 548 && xCoord <= 654 && yCoord >= 264 && yCoord <= 320 && pixelColour != -14)
            hazardClicked ("short circuit");
    }


    /**
     * This method opens an informational window upon a hazard being clicked.
     * 
     * @param hazard The name of the hazard clicked.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> infoText </b> The JLabel added to the window containing information about the hazard clicked.
     * <p>
     * <b> heading </b> The JLabel that is used as a heading in the window.
     */ 
    private void hazardClicked (String hazard)
    {
        JLabel infoText = new JLabel ();
        JLabel heading = new JLabel ();
        infoDialog = new JDialog (frame, "Hazard Found");
        allHazardsFound = true;

        //window set up
        infoText.setFont (new Font ("Serif", Font.PLAIN, 20));
        heading.setFont (new Font ("Serif", Font.PLAIN, 20));
        infoDialog.setSize (430, 270);
        infoDialog.getContentPane ().setBackground(new Color (255, 224, 117));
        infoDialog.setResizable(false);
        infoDialog.setLocationRelativeTo(frame);
        infoDialog.setLayout (new FlowLayout ());
        infoDialog.add (heading);
        infoDialog.add (infoText);
        infoDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //set text on window based on the hazard clicked
        if (hazard.equals ("napkin"))
        {
            heading.setText ("You clicked the NAPKIN");
            infoText.setText ("<html> <body width = 340> <br/> Make sure to keep flammable items like fabric, " +
                              "paper, and wood away from any heat source! </html>");
            hazardsFound [hazardNames.indexOf (hazard)] = true;
        }
        else if (hazard.equals ("aerosol can"))
        {
            heading.setText ("You clicked the AEROSOL CAN");
            infoText.setText ("<html> <body width = 340> <br/> Flammable liquids/sprays and pressurized cans " +
                              "must be kept away from heat sources. If heated, a pressurized can may explode. </html>");
            hazardsFound [hazardNames.indexOf (hazard)] = true;
        }
        else if (hazard.equals ("dusty vent"))
        {
            heading.setText ("You clicked the DUSTY VENT");
            infoText.setText ("<html> <body width = 340> <br/> Dust and soot accumulated in vents is a fire " +
                              "hazard. It is composed of dry and flammable material which will catch fire if it " +
                              "becomes hot or is exposed to sparks.");
            hazardsFound [hazardNames.indexOf (hazard)] = true;
        }
        else if (hazard.equals ("overloaded outlet"))
        {
            heading.setText ("You clicked the OVERLOADED OUTLET");
            infoText.setText ("<html> <body width = 330> <br/> Do not overload any outlets, even with the use of " + 
                              "an extension. Having too many applicances plugged in will cause heat to build up " +
                              "due to resistance. This can cause a fire.");
            hazardsFound [hazardNames.indexOf (hazard)] = true;
        }
        else
        {
            heading.setText ("You clicked the SHORT CIRCUIT");
            infoText.setText ("<html> <body width = 330> <br/> Be very careful with wires and electrical devices " +
                              "around water. If any part of a wire comes into contact with water, a short circuit " +
                              " will be created. The excess current can cause a fire through overheating.");
            hazardsFound [hazardNames.indexOf (hazard)] = true;
        }
        infoDialog.setVisible (true);
        
        //pause timer upon opening the window
        t.pauseTimer (); //version 4.0 June 7 modification, no longer uses pause() as it is deprecated
        resumeWhenClosed (); //version 4.0 June 9 modification, window listener moved to another method for organization
    }

    
    //version 4.0 June 9 addition, infoDialog window listener moved to this method for organization
    /**
     * This method resumes the timer when infoDialog is closed. It instead opens the "Level Complete" JDialog if all
     * hazards have been found.
     */ 
    private void resumeWhenClosed ()
    {
        infoDialog.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                infoDialog = null; //indicates that the window is closed
                //check if all hazards are found
                for (int x = 0; x < hazardsFound.length; x++)
                {
                    if (hazardsFound[x] == false)
                    {
                        allHazardsFound = false;
                    }
                }
                //open level completion window after delay if all hazards are found
                if (allHazardsFound)
                {
                    try 
                    {
                        Thread.sleep (700);
                    }
                    catch (InterruptedException ie)
                    {
                    }
                    levelComplete ();
                    t.endTimer (); //ends the timer to stop errors caused by the running Thread
                }
                //resume timer after closing window if hazards remain undiscovered
                else 
                {
                    t.resumeTimer (); //version 4.0 June 7 modification, no longer uses resume() as it is deprecated
                }
            }
        });
    }
    

    /**
     * This method opens a window upon all hazards being found.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> winDialog </b> The window that is opened when the level is won.
     * <p>
     * <b> heading </b> The JLabel used as the heading of the JDialog.
     */ 
    private void levelComplete ()
    {
        JDialog winDialog = new JDialog (frame);
        JLabel heading = new JLabel ();
        endDialog = winDialog; //version 4.0 June 7 addition, sets this JDialog as the end of game JDialog

        //version 4.0 June 7 additions, set up a JLabel to be used as a heading
        heading.setFont (new Font ("Serif", Font.BOLD, 40));
        heading.setText ("Level Complete!");
        
        winDialog.setSize (510, 400);
        winDialog.setLayout (null); //version 4.0 June 9 addition, allows absolute positioning
        winDialog.setUndecorated(true);
        winDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        winDialog.getContentPane ().setBackground(new Color (128, 210, 145));
        winDialog.add (heading); //version 4.0 June 7 addition, adds a heading to the JDialog
        heading.setLocation (120, 30); //version 4.0 June 9 addition, set location needed for null layout
        heading.setSize (400, 50); //version 4.0 June 9 addition, set size needed for null layout
        winDialog.setResizable(false);
        winDialog.setLocationRelativeTo(frame);
        winDialog.setVisible (true);
    }
    
    
    /**
     * This method opens a window upon the time running out.
     * <p>
     * <b> Local Variables </b>
     * <p>
     * <b> loseDialog </b> The window that is opened when time runs out.
     * <p>
     * <b> heading </b> The JLabel used as the heading for the JDialog.
     */ 
    private void gameOver ()
    {
        JDialog loseDialog = new JDialog (frame);
        JLabel heading = new JLabel ();
        endDialog = loseDialog; //version 4.0 June 7 addition, sets this JDialog as the end of game JDialog

        //version 4.0 June 7 additions, set up a JLabel to be used as the heading
        heading.setFont (new Font ("Serif", Font.BOLD, 40));
        heading.setText ("You Lose");
        
        loseDialog.setSize (510, 400);
        loseDialog.setLayout (null); //version 4.0 June 9 addition, allows absolute positioning
        loseDialog.setUndecorated(true);
        loseDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        loseDialog.getContentPane ().setBackground(new Color (248, 131, 134));
        loseDialog.add (heading); //version 4.0 June 7 addition, adds a heading to the JDialog
        heading.setLocation (170, 30); //version 4.0 June 9 addition, set location needed for null layout
        heading.setSize (400, 50); //version 4.0 June 9 addition, set size needed for null layout
        loseDialog.setResizable(false);
        loseDialog.setLocationRelativeTo(frame);
        loseDialog.setVisible (true);
    }
    
    
    //version 4.0 June 9 addition, allows a ContinueMenu to be added to the JDialog from the driver class
    /**
     * Returns the end of game JDialog when one is created.
     * 
     * @return The JDialog created at the end of the level.
     */ 
    public JDialog getEndDialog ()
    {
        while (endDialog == null)
        {
        }
        return endDialog;
    }
    
    
    //version 4.0 June 9 addition, allows the driver class to check whether all hazards have been found in this level
    /**
     * This method returns whether or not all hazards have been found.
     * 
     * @return A boolean representing whether or not all hazards have been found.
     */ 
    public boolean noHazardsLeft ()
    {
        return allHazardsFound;
    }
    
    
    //version 4.0 June 9 addition, allows the driver class to clear the frame before adding the next level
    /**
     * This method erases all level one components from the JFrame when the level ends.
     */ 
    public void eraseComponents ()
    {
        frame.remove (label);
        endDialog.dispose ();
        t.removeTimer ();
    }
    //version 4.0 June 8 modification, main method removed as a separate driver class is now used
}