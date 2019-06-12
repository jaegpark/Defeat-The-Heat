package Maze;

import Main.DefeatTheHeat;

import java.awt.*;

/**
 * The Player class. Holds the data for the player on the board.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 1.0
 *
 * Created framework and functionality.
 */
public class Player {
    /**
     * THe image that is used to represent the character on the board.
     */
    public Image sprite;
    /**
     * The current x position of the character on board.
     */
    public int x;
    /**
     * The current y position of the character on board.
     */
    public int y;

    /**
     * The image of the heart that shows the Health Points of the player.
     */
    public Image hp;

    /**
     * This is the constructor that initializes all field values, and sets the starting
     * position of the character in the maze.
     *
     * @param path - The file directory of the sprite image.
     */
    public Player(String path) {
        sprite = DefeatTheHeat.imageFromFile(path);
        x = 0;  // first column
        y = 1;  // second row
        // Tileset used: https://opengameart.org/content/heart-3
        hp = DefeatTheHeat.imageFromFile("Maze/Images/hearts.png");
    }

}
