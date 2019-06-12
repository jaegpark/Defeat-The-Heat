package Maze;

import Menu.MenuOption;
import java.awt.*;

/**
 * The Pause class for the pause menu
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 1.0
 * Created basic framework and finished everything.
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
     * The list of menu options in the pause menu.
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
