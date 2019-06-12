package Menu;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;

/**
 * The MenuOption that exists in the pause, continue, and main menus. Contains the
 * data for one menu option.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 *
 * @version 2.0
 * 1
 * Created basic framework and functionality.
 * 2
 * Changed the MenuOption class to extend JLabel for compatibility with
 * transitions in levels 1 and 2.
 */
public class MenuOption extends JLabel {

    /** The Point that holds the position of the MenuOption */
    public Point position;
    /** The Image that represents this menu option to the user */
    public Image image;

    /**
     *  The constructor, that creates a new MenuOption given an image and location.
     *
     * @param path - The file path of the image
     * @param location - The location on the screen where the option is displayed
     */
    public MenuOption(String path, Point location){
        image = DefeatTheHeat.imageFromFile(path);
        this.position = location;
    }

    /**
     *  Draws the current menu option at the specified point coordinates
     */
    public void draw(){
        DefeatTheHeat.graphics.drawImage(image, position.x, position.y, null);
    }

    /**
     *  Enlarges the image of the menu option when the user hovers over it.
     */
    public void enlarge(){
        Dimension oldSize = new Dimension(image.getWidth(null), image.getHeight(null));
        Dimension newSize = new Dimension((int)(image.getWidth(null)*1.3), (int)(image.getHeight(null)*1.3));
        DefeatTheHeat.graphics.drawImage(image, position.x - (newSize.width - oldSize.width)/2, position.y - (newSize.height - oldSize.height)/2, newSize.width, newSize.height,null);
    }


}
