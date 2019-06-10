package Maze;

import Main.DefeatTheHeat;

import java.awt.*;
import java.util.*;

/**
 * This is the Board class that holds the data for the maze map.
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 1.0
 *
 * Added javadoc, finished everything.
 */
public class Board {
    /**
     * The Dimension that holds the size dimensions of the maze size
     */
    public Dimension mapSize;
    /**
     * The Point that stores the starting point value coordinates
     */
    public Point start;
    /**
     * The Point that stores the ending point value coordinates
     */
    public Point end;
    /**
     * The boolean 2-D array that determines if the position is a wall or not
     */
    public char [][] map;
    /**
     * The image of the maze
     */
    public Image background;

    /**
     * The default constructor that takes in the path for the maze file directory. Initializes all
     * default values.
     *
     * @param path The pth of the map file
     */
    public Board(String path) {
        // Tileset used: https://opengameart.org/content/dungeon-tileset
        background = DefeatTheHeat.imageFromFile("Maze/Images/maze.png");
        start = new Point();
        end = new Point();
        mapSize = new Dimension(600, 600);
        Scanner sc = new Scanner(DefeatTheHeat.class.getClassLoader().getResourceAsStream(path));
        mapSize.width = sc.nextInt();
        mapSize.height = sc.nextInt();
        map = new char[mapSize.width][mapSize.height];
        for (int i = 0; i < mapSize.height; i++) {
            String temp = sc.next();
            for (int j = 0; j < mapSize.width; j++) {
                map[j][i] = temp.charAt(j);
            }
        }
    }
}
