package Maze;

import Main.DefeatTheHeat;
import Menu.MenuOption;
import java.awt.*;
import java.util.*;

/**
 * The Pause class for the pause menu
 *
 * @author Jae Park
 */
public class Pause {

    /**
     * The selected option index.
     */
    public int selected;

    /**
     * The background image for the pause menu.
     */
    public Image background;

    /**
     * The list of menuoptions in the pause menu.
     */
    public MenuOption[] options;

    /**
     * Default constructor initializes the images.
     */
    public Pause(){
        options = new MenuOption[3];
        options[2] = new MenuOption("Maze/Images/exit.png", new Point (250, 300));
        options[1] = new MenuOption("Maze/Images/instructions.png", new Point (250, 200));
        options[0] = new MenuOption("Maze/Images/resume.png", new Point (250, 100));
    }

}
