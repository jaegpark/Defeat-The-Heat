package Maze;

import Main.DefeatTheHeat;

import java.awt.*;


/**
 * This class is used for objects (hazards and items) on the maze
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 1.0
 * Created framework and functionality.
 */
public class ObjectsInMaze {
    /**
     * The ID of the object.
     */
    protected String id;
    /**
     * The icon of the ObjectOnBoard.
     */
    protected Image icon;
    /**
     * The Point that holds the graphics position of the image.
     */
    protected Point coordinates;

    /**
     * Constructor takes in and initializes all the field values.
     *
     * @param s - The String that represents the id of the object.
     * @param path - The String that indicates the file directory of the icon.
     * @param pos - The Point that defines the object's position.
     */
    public ObjectsInMaze(String s, String path, Point pos){
        id = s;
        icon = DefeatTheHeat.imageFromFile(path);
        coordinates = pos;
    }

    /**
     * This method returns the String value of the id of the object.
     *
     * @return id
     */
    public String getId(){
        return id;
    }

    /**
     * Draws the object at the location determined by the Point field.
     */
    public void draw(){
        DefeatTheHeat.graphics.drawImage(icon, coordinates.x, coordinates.y, null);
    }
}
