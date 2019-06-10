package Maze;

import java.awt.*;

/**
 * This Hazard class stores all data for a hazard in the maze.
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 2.0
 * 1
 * Created basic framework and functionality.
 * 2
 * Made it extend ObjectsInMaze and simplified everything.
 *
 */
public class Hazard extends ObjectsInMaze {
    /**
     * Constructor takes in and initializes all the field values.
     *
     * @param s - The String that represents the id of the object.
     * @param path - The String that indicates the file directory of the icon.
     * @param pos - The Point that defines the object's position.
     */
    public Hazard(String s, String path, Point pos){
        super(s,path,pos);
    }

}
