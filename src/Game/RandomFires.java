package Game; 

import Main.DefeatTheHeat;

import javax.swing.*;
import java.util.*;

/**
 * This class randomly displays fires at set locations on the passed in JFrame.
 * 
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 * 
 * @author Rohan Krishna
 * @version 4.0 June 10, 2019
 * <p>
 * <b> Total time spent writing file: </b> 5.5 hours
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> frame </b> The JFrame on which the fires are to be displayed.
 * <p>
 * <b> itemsArray </b> An ArrayList of JLabels storing the equipment that can be used to extinguish fire.
 * <p>
 * <b> background </b> The JLabel used as the background for the JFrame.
 * <p>
 * <b> fire1 </b> The fire at the first position from the left, displayed using a JLabel.
 * <p>
 * <b> fire2 </b> The fire at the second position from the left, displayed using a JLabel.
 * <p> 
 * <b> fire3 </b> The fire at the third position from the left, displayed using a JLabel.
 * <p>
 * <b> fire4 </b> The fire at the last position from the left, diplayed using a JLabel.
 * <p>
 * <b> activeFires </b> The number of fires that are currently displayed.
 * <p>
 * <b> fireCount </b> The number of fires that have been put out.
 */ 
public class RandomFires extends TimerTask
{
    private JFrame frame;
    private ArrayList<JLabel> itemsArray;
    private JLabel background;
    private JLabel fire1;
    private JLabel fire2;
    private JLabel fire3;
    private JLabel fire4;
    private int activeFires;
    private int fireCount;
    
    /**
     * Initializes the JFrame on which to display fires, as well as the JLabel to store the background image and the ArrayList of equipment.
     * 
     * @param window The JFrame on which to display the fires.
     * @param image The JLabel used as the background of the JFrame.
     * @param itemsArr An ArrayList of JLabels which represent equipment to extinguish fires.
     */ 
    public RandomFires (JFrame window, JLabel image, ArrayList<JLabel> itemsArr)
    {
        frame = window;
        background = image;
        itemsArray = itemsArr;
    }
    
    
    /**
     * This method displays a fire at one of four locations, choosen randomly.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> num </b> Stores a random number from 0 - 9 to determine if and where to display a fire.
     * <p>
     * <b> fire </b> The JLabel that is used to display a fire.
     */ 
    public void startFire ()
    {
        int num = (int) (Math.random () * 10); //creates 40% chance to display a fire
        
        if (num <= 3) {
            JLabel fire = null;
            // Source for fire image: www.kisspng.com/free/yellow-fire.html
            if (num == 0 && fire1 == null) {
                fire1 = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/fire.png")));
                fire = fire1;
                fire1.setLocation (65, 140);
                activeFires++;
            }
            else if (num == 1 && fire2 == null) {
                fire2 = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/fire.png")));
                fire = fire2;
                fire2.setLocation (220, 310);
                activeFires++;
            }
            else if (num == 2 && fire3 == null) {
                fire3 = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/fire.png")));
                fire = fire3;
                fire3.setLocation (430, 180);
                activeFires++;
            }
            else if (num == 3 && fire4 == null) {
                fire4 = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Game/fire.png")));
                fire = fire4;
                fire4.setLocation (615, 15);
                activeFires++;
            }
            
            if (fire != null)
            {
                fire.setSize(100, 100);
                frame.add(fire);
                frame.add(background);
                fire.setText(""); //forces revalidate
            }
        }
    }
    
    
    /**
     * This method hides a fire upon the appropriate item being dragged over it to extinguish it.
     * 
     * @param x The x coordinate where the mouse is released to use the item.
     * @param y The y coordinate where the mouse is released to use the item.
     * @param item The JLabel representing an item to put out fire.
     */ 
    public void eraseFire (int x, int y, JLabel item)
    {
        if (fire1 != null && (item.equals(itemsArray.get(2)) || item.equals(itemsArray.get(3))) && 
            x >= 65 && x <= 165 && y >= 140 && y <= 240)
        {
            fire1.setVisible (false);
            fire1 = null;
            activeFires--;
            fireCount++;
        }
        else if (fire2 != null && item.equals(itemsArray.get(1)) && x >= 220 && x <= 320 && y >= 310 && y <= 410)
        {
            fire2.setVisible (false);
            fire2 = null;
            activeFires--;
            fireCount++;
        }
        if (fire3 != null && (item.equals(itemsArray.get(2)) || item.equals(itemsArray.get(3))) && 
            x >= 430 && x <= 530 && y >= 180 && y <= 280)
        {
            fire3.setVisible (false);
            fire3 = null;
            activeFires--;
            fireCount++;
        }
        else if (fire4 != null && item.equals(itemsArray.get(0)) && x >= 615 && x <= 715 && y >= 15 && y <= 115)
        {
            fire4.setVisible (false);
            fire4 = null;
            activeFires--;
            fireCount++;
        }
    }
    
    
    /**
     * Returns whether level two has been lost or won depending on the fires put out and the fires currently active.
     * 
     * @return "win" if 15 fires have been put out, "lose" if 4 fires are active.
     */ 
    public String alertWhenFinished ()
    {
        while (fireCount < 15)
        {
            if (activeFires == 4) {
                cancel ();
                return "lose";
            }
            System.out.print (""); //allows the JFrame to update
        }
        return "win";
    }
    
    
    /**
     * This method is called at intervals that the TimerTask is scheduled to run. A fire is created periodically.
     */ 
    public void run ()
    {
        if (fireCount < 15)
            startFire ();
        else
            cancel ();
    }
}