package Game; //version 4.0 June 8 addition, allows this class to be used from the driver class

import java.awt.*;
import javax.swing.*;

/**
 * This class is used to create a timer that can be added to a JFrame.
 * 
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 * 
 * @author Rohan Krishna
 * @version 4.0 June 10, 2019
 * <p>
 * <b> Time spent modifying file: </b> 0.5 hours
 * <p>
 * <b> Total time spent writing file: </b> 7 hours
 * <p>
 * <b> Modification summary: </b>
 * The methods pauseTimer() and resumeTimer() have been added to replace Thread.suspend() and Thread.resume(), which
 * are deprecated methods.
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> panel </b> The JPanel on which the time is displayed.
 * <p>
 * <b> xCoord </b> The x coordinate on the window that the timer is to be displayed at.
 * <p>
 * <b> yCoord </b> The y coordinate on the window that the timer is to be displayed at.
 * <p>
 * <b> seconds </b> The total seconds remaining, with each minute being 60 seconds.
 * <p>
 * <b> minutes </b> The total minutes remaining, with every 60 seconds being one minute.
 * <p>
 * <b> displayLoc </b> The JFrame on which the timer is to be displayed.
 * <p>
 * <b> timerPaused </b> Stores whether or not the timer is paused.
 */ 
public class TimeDisplay extends Thread
{
    private JPanel panel; //version 4.0 June 9 addition, made instance level so the JPanel can be removed in a method
    private int xCoord;
    private int yCoord;
    private int seconds;
    private int minutes;
    private JFrame displayLoc;
    private boolean timerPaused; //version 4.0 addition June 7 addition, flag variable used to pause instead of resume()/pause()

    /**
     * This constructor initializes the seconds, minutes, container, and coordinates based on values passed.
     * 
     * @param window The JFrame on which the timer is to be displayed.
     * @param x The x coordinate at which the timer is to be displayed.
     * @param y The y coordinate at which the timer is to be displayed.
     * @param sec The total seconds from which the timer is to count down.
     */ 
    public TimeDisplay (JFrame window, int x, int y, int sec)
    {
        displayLoc = window;
        xCoord = x;
        yCoord = y;
        seconds = sec;
        minutes = seconds / 60;
    }


    /**
     * This method adds the timer to the JFrame and executes the countdown.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> timeText </b> The JLabel used to display the time.
     * <p>
     * <b> panel </b> The JPanel that contains the time and is added to the JFrame.
     */ 
    private void displayTime ()
    {
        JLabel timeText = new JLabel (); 
        panel = new JPanel (); //version 4.0 June 9 modification, made instance level so the JPanel can be removed in another method
        
        //set up JPanel
        timeText.setPreferredSize(new Dimension (180, 50));
        timeText.setHorizontalAlignment (JLabel.CENTER);
        timeText.setFont (new Font ("Monospaced", Font.BOLD, 30));
        timeText.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));
        panel.setSize(new Dimension (180, 50));
        panel.setLayout (new FlowLayout (FlowLayout.CENTER, 0, 0));
        panel.setBackground (Color.PINK);
        panel.add (timeText);
        displayLoc.getContentPane ().add (panel);
        panel.setLocation (xCoord, yCoord);

        try
        {
            //display original time from which to count down from
            Thread.sleep (100);
            displayLoc.revalidate();
            timeText.setText (minutes + " : " + String.format ("%02d", seconds % 60));
            //timer countdown
            while (seconds > 0)
            {
                Thread.sleep(1000);
                if (!timerPaused)
                {
                    seconds--;
                    minutes = (seconds / 60);
                    timeText.setText(minutes + " : " + String.format("%02d", seconds % 60));
                }
                displayLoc.revalidate();
            }
        }
        catch (InterruptedException e)
        {
        }
    }


    //version 4.0 June 7 addition, this method is used in place of pause() as it is deprecated
    /**
     * This method is used to pause the timer.
     */
    public void pauseTimer ()
    {
        timerPaused = true;
    }


    //version 4.0 June 7 addition, this method is used in place of resume(), as it is deprecated
    /**
     * This method is used to resume the timer.
     */
    public void resumeTimer ()
    {
        timerPaused = false;
    }


    /**
     * This method can be used by a client class to create a delay until the timer reaches 0.
     */ 
    public void waitUntilFinish ()
    {
        while (seconds > 0)
        {
            System.out.print ("");
        }
    }
    
    
    /**
     * This method returns whether or not the timer has reached 0.
     * 
     * @return true if the timer has reached 0, false otherwise.
     */ 
    public boolean isFinished ()
    {
        return seconds == 0;
    }
    
    
    //version 4.0 June 9 addition, allows timer to be removed
    /**
     * This method removes the timer JPanel from the JFrame.
     */ 
    public void removeTimer ()
    {
        displayLoc.remove (panel);
    }
    
    
    //version 4.0 June 9 addition, allows the timer to be set to 0 when the game is won to prevent Thread errors
    /**
     * This method sets the timer to 0.
     */ 
    public void endTimer ()
    {
        seconds = 0;
    }


    /**
     * This method is called when the Thread starts and it starts the timer countdown.
     */ 
    public void run ()
    {
        displayTime ();
    }
}
