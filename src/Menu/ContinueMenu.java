package Menu;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This is the menu in between each level.
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 *
 * @version 1.0
 * Copied the contents of the MainMenu class, and re-initialized the methods for the different usage.
 */
public class ContinueMenu extends JLabel {
    /**
     * The image of the menu background.
     */
    private Image backgroundImage;
    /**
     * The list of menu options.
     */
    private MenuOption[] displayedOptions;
    /**
     * Holds the condition whether or not the user has chosen a valid option.
     */
    private volatile boolean choseValid;
    /**
     * The int value of the user's selected option.
     */
    private int selected;

    /**
     * Default constructor- initializes all default values, sets the input map for keyboard input, and
     * sets up action calls depending on keyboard input.
     */
    public ContinueMenu() {
        selected = 0;
        choseValid = false;
        //backgroundImage = DefeatTheHeat.imageFromFile("Menu/continue.png");

        displayedOptions = new MenuOption[2];
        displayedOptions[0] = new MenuOption("Menu/continueGame.png", new Point(150, 170)); //300, 200
        displayedOptions[1] = new MenuOption("Menu/exit.png", new Point(200, 270)); //350, 300

        InputMap keyboardInput = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        keyboardInput.clear();
        getActionMap().clear();

        keyboardInput.put(KeyStroke.getKeyStroke('w'), "up");
        keyboardInput.put(KeyStroke.getKeyStroke("UP"), "up");
        keyboardInput.put(KeyStroke.getKeyStroke('s'), "down");
        keyboardInput.put(KeyStroke.getKeyStroke("DOWN"), "down");
        keyboardInput.put(KeyStroke.getKeyStroke("ENTER"), "select");
        getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected > 0) {
                    selected--;
                    repaint();
                }
            }
        });
        getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected < 1) {
                    selected++;
                    repaint();
                }
            }
        });
        getActionMap().put("select", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choseValid = true;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        DefeatTheHeat.graphics = (Graphics2D) g;
        //g.drawImage(backgroundImage, 0, 0, null);
        //g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(0,0,getWidth(),getHeight());
        for (int i = 0; i < displayedOptions.length; i++) {
            if (i == selected) {
                displayedOptions[i].enlarge();
            } else {
                displayedOptions[i].draw();
            }
        }
    }

    /**
     * Method call continually updates the screen until the user has chosen a valid option.
     *
     * @return - returns the int value that represents the selected option.
     */
    public int getOption() {
        revalidate();
        repaint();
        while (!choseValid) ;
        return selected;
    }
}

