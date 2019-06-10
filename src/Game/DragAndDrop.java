package Game; 

import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;

/**
 * This class allows the use of a drag and drop interface. JLabels can be clicked and dragged on a window.
 * 
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 * 
 * @author Rohan Krishna
 * @version 4.0 June 10, 2019
 * <p>
 * <b> Total time spent writing file: </b> 8.5 hours
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> mouseIsPressed </b> Indicates whether the mouse is currently being pressed.
 * <p>
 * <b> startX </b> The x coordinate of the initial mouse click.
 * <p>
 * <b> startY </b> The y coordinate of the initial mouse click.
 * <p>
 * <b> originalCoords </b> An int array storing the original x and y coordinates of all moveable items.
 * <p>
 * <b> item </b> The JLabel that is currently selected.
 * <p>
 * <b> itemsArray </b> JLabel ArrayList of all moveable JLabels.
 * <p>
 * <b> clientFrame </b> The JFrame that is using the drag and drop interface.
 */ 
public class DragAndDrop extends Thread
{
    private boolean mouseIsPressed = false;
    private int startX;
    private int startY;
    private int[][] originalCoords; //set values used instead of methods to get x and y location to prevent errors when the mouse is moved quickly
    private JLabel item;
    private ArrayList<JLabel> itemsArray;
    private JFrame clientFrame;
    
    /**
     * This constructor initializes an array of JLabels and the initial coordinates of each JLabel. The client JFrame is also set.
     * 
     * @param frame The JFrame that is using the drag and drop interface.
     * @param itemsArr An ArrayList of JLabels that can be dragged.
     */ 
    public DragAndDrop (JFrame frame, ArrayList<JLabel> itemsArr)
    {
        clientFrame = frame;
        itemsArray = itemsArr;
        originalCoords = new int[itemsArr.size ()][2];
        
        for (JLabel j : itemsArray)
        {
            originalCoords[itemsArray.indexOf (j)][0] = j.getX ();
            originalCoords[itemsArray.indexOf (j)][1] = j.getY ();
        }
    }
    
    
    /**
     * This method relocates a JLabel depending on the current position of the mouse.
     * <p>
     * <b> Local Variables: </b> 
     * <p>
     * <b> originalItemX </b> Stores the original x coordinate of the JLabel that is clicked.
     * <p>
     * <b> originalItemY </b> Stores the original y coordinate of the JLabel that is clicked.
     */ 
    public void moveItem ()
    {
        if (mouseIsPressed && item != null)
        {
            int originalItemX = originalCoords [itemsArray.indexOf (item)][0];
            int originalItemY = originalCoords [itemsArray.indexOf (item)][1];
        
            //sets new location of JLabel based on where the cursor has moved to
            item.setLocation (originalItemX + (int) (MouseInfo.getPointerInfo ().getLocation ().getX () - clientFrame.getLocationOnScreen().getX () - startX), 
                              originalItemY + (int) (MouseInfo.getPointerInfo ().getLocation ().getY () - clientFrame.getLocationOnScreen().getY () - startY) - 30);
            clientFrame.revalidate ();
        }
        System.out.print (""); //forces JFrame to revalidate 
    }
    
    
    /**
     * This method assigns values to variables to indicate that the mouse has been clicked. The coordinates of the click and the JLabel that is
     * clicked is also set.
     * 
     * @param x The x coordinate where the mouse was initially pressed.
     * @param y The y coordinate where the mouse was initially pressed.
     * @param itemPressed The JLabel that was clicked on.
     */ 
    public void pressMouse (int x, int y, JLabel itemPressed)
    {
        mouseIsPressed = true;
        startX = x;
        startY = y;
        item = itemPressed;
    }
    
    
    /**
     * This method indicates that the mouse has been released when it is called. It also resets the position of all JLabels to their original
     * coordinates. The JLabel that was previously clicked is now deselected.
     */ 
    public void releaseMouse ()
    {
        mouseIsPressed = false;
        if (item != null)
        {
            //resetting locations of all items upon mouse release accommodates for coordinate mix up within program when mouse is moved quickly
            for (JLabel j : itemsArray)
            {
                j.setLocation (originalCoords [itemsArray.indexOf (j)][0], originalCoords [itemsArray.indexOf (j)][1]);
            }
            item = null;
        }
    }
    
    
    /**
     * This method is called when the Thread starts. The method moveItem() is continually called to update the position of a selected JLabel.
     */ 
    public void run ()
    {
        while (true)
        {
            try
            {
                Thread.sleep (20);
            }
            catch (InterruptedException e)
            {
            }
            moveItem ();
        }
    }
}