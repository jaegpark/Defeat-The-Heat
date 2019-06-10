package Menu;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * The MainMenu class, which displays game options to the user.
 *
 * @author Jae Park
 * @version 1.0, 2019-05-24
 */
public class MainMenu extends JPanel {


    /**
     * An array of MenuOptions
     */
    private MenuOption[] displayedOptions;
    /**
     * The background image of the Menu
     */
    private Image backgroundImage;
    /**
     * The selected option
     */
    private int selected;
    /**
     * Whether the user has selected their option or not
     */
    private volatile boolean choseValid;
    /**
     * The image to indicate the selected option
     */
    private Image selector;

    /**
     * Default constructor - constructs the JPanel
     */
    public MainMenu() {
        choseValid = false;
        selected = 0;

        InputMap newInput = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        newInput.clear();
        getActionMap().clear();
        newInput.put(KeyStroke.getKeyStroke('w'), "up");
        newInput.put(KeyStroke.getKeyStroke("UP"), "up");
        newInput.put(KeyStroke.getKeyStroke('s'), "down");
        newInput.put(KeyStroke.getKeyStroke("DOWN"), "down");
        newInput.put(KeyStroke.getKeyStroke("ENTER"), "select");
        getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected > 0) {
                    selected--;
                }
                repaint();
            }
        });
        getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected < 2) {
                    selected++;
                }
                repaint();
            }
        });
        getActionMap().put("select", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choseValid = true;
            }
        });
        displayedOptions = new MenuOption[3];
        displayedOptions[0] = new MenuOption("Menu/newGame.png", new Point(280, 200));
        displayedOptions[1] = new MenuOption("Menu/continueGame.png", new Point(300, 300));
        displayedOptions[2] = new MenuOption("Menu/exit.png", new Point(340, 400));

        // Retrieves the menu background from the file location
        backgroundImage = DefeatTheHeat.imageFromFile("Menu/mainMenu2.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        DefeatTheHeat.graphics = (Graphics2D) g;
        g.drawImage(backgroundImage, 0, 0, null);
        for (int i = 0; i < displayedOptions.length; i++) {
            if (i == selected) {
                displayedOptions[i].enlarge();
            } else displayedOptions[i].draw();
        }
    }

    /**
     * This method determines the user selection in the main menu.
     *
     * @return - returns the int value of the selected option of the user in the main menu.
     */
    public int getOption() {
        revalidate();
        repaint();
        while (!choseValid) ;
        getActionMap().clear();
        return selected;
    }
}
