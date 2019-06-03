package Menu;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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
        selected = -11;

        InputMap newInput = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        displayedOptions = new MenuOption[3];
        displayedOptions[0] = new MenuOption("Menu/newGame.png", new Point(300, 200));
        displayedOptions[1] = new MenuOption("Menu/continueGame.png", new Point(300, 300));
        displayedOptions[2] = new MenuOption("Menu/exit.png", new Point(300, 400));

        // Retrieves the menu background from the file location
        backgroundImage = DefeatTheHeat.imageFromFile("Menu/MainMenu1.png");
        selector = DefeatTheHeat.imageFromFile("Menu/selector.png");
        //TODO: Fix the bug where the underline pointer is redrawing itself at the first location
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                if (m.getX() > 300 && m.getX() < 600 && m.getY() > 200 && m.getY() < 307) {
                    selected = 0;
                    repaint();
                    choseValid = true;
                } else if (m.getX() > 300 && m.getX() < 600&& m.getY() > 307 && m.getY()<414) {
                    selected = 1;
                    repaint();
                    choseValid = true;

                } else if (m.getX() > 300 && m.getX() < 600&& m.getY() > 414 && m.getY()<521) {
                    selected = 2;
                    repaint();
                    choseValid = true;
                }
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        DefeatTheHeat.graphics = (Graphics2D) g;
        g.drawImage(backgroundImage, 0, 0, null);
        for (MenuOption cur : displayedOptions)
            cur.draw();
        g.drawImage(selector, 290, 200+selected*107+40, null);
    }

    public int getOption() {
        revalidate();
        repaint();
        while (!choseValid) ;
        return selected;
    }

    public void checkLocation(int x, int y) {
        if (x > 300 && x < 600) {
            if (y > 200 && y < 307) {

            }
        }
    }

}
