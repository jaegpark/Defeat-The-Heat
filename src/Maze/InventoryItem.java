package Maze;

import Main.DefeatTheHeat;
import java.awt.*;

/**
 * The InventoryItem class.
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 2.0
 * 1
 * Created basic framework and functionality.
 * 2
 * Made it extend the ObjectsInMaze class to improve clarity.
 */
public class InventoryItem extends ObjectsInMaze {

    /**
     * Constructor takes in and initializes all the field values.
     *
     * @param s - The String that represents the id of the object.
     * @param path - The String that indicates the file directory of the icon.
     * @param pos - The Point that defines the object's position.
     */
    public InventoryItem(String s, String path, Point pos){
        super(s,path,pos);
    }

    /**
     *  Enlarges the image of the menu option when the user hovers over it.
     */
    public void enlarge(){
        Dimension oldSize = new Dimension(icon.getWidth(null), icon.getHeight(null));
        Dimension newSize = new Dimension((int)(icon.getWidth(null)*1.1), (int)(icon.getHeight(null)*1.1));
        DefeatTheHeat.graphics.drawImage(icon, coordinates.x - (newSize.width - oldSize.width)/2, coordinates.y - (newSize.height - oldSize.height)/2, newSize.width, newSize.height,null);
    }
}
