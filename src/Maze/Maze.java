package Maze;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * This is the main game panel for the third level
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 *
 * @version 9.0
 * Version history:
 * 1
 * Created basic framework.
 * 2
 * Created basic I/O with keyboard. Assigned each action map to different empty methods.
 * 3
 * Filled in movePlayer and other methods. Finished entire game code.
 * 4
 * Finished implementing the pause menu.
 * 5
 * Finished implementing the hazard images and inventory images.
 * 6
 * Finished making an actual maze background.
 * 7
 * Created the instructions menu.
 * 8
 * Finished the javadoc.
 * 9
 * Added the rules panel in the beginning of the game.
 */
public class Maze extends JPanel {

    /**
     * This checks whether the user wants to quit from the main menu.
     */
    private volatile boolean wantsToQuit;
    /**
     * This checks whether the pause menu is displayed or not.
     */
    private volatile boolean pauseOpen;
    /**
     * This checks whether the game is won or not.
     */
    public volatile boolean isWon;
    /**
     * This keeps the list of inventory items the user has.
     */
    private ArrayList<InventoryItem> items;
    /**
     * The index of the current item in hand.
     */
    private int currentItem;
    /**
     * The Board that holds each tile value of the maze.
     */
    private Board map;
    /**
     * The Player the user controls.
     */
    private Player character;
    /**
     * The Pause menu class.
     */
    private Pause pauseMenu;
    /**
     * The list of all the hazards that are inside the maze.
     */
    private ArrayList<Hazard> hazards;
    /**
     * The amount of lives the player has left.
     */
    private int life;
    /**
     * The amount of obstacles the user has cleared.
     */
    private int count;
    /**
     * Checks if the user has selected to see the instructions or not.
     */
    private boolean selectedInstructions;
    /**
     * Displays the rules of the level.
     */
    private JDialog rulesDialog;
    /**
     * Condition where the game is done or not.
     */
    private volatile boolean gamedone;

    /**
     * The default constructor.
     * Sets the default values for the new game, and sets up keyboard input, and calls proper methods based on
     * user attempts at interacting with hazards.
     */
    public Maze() {
        gamedone = false;
        count = 0;
        selectedInstructions = false;
        items = new ArrayList<InventoryItem>();
        hazards = new ArrayList<Hazard>();
        life = 3;
        wantsToQuit = false;
        isWon = false;
        currentItem = 0;
        map = new Board("Maze/map.txt");
        character = new Player("Maze/Images/player.png");
        pauseMenu = new Pause();

        items.add(new InventoryItem("water", "Maze/Images/waterEx.PNG", new Point(610, 1)));
        items.add(new InventoryItem("powder", "Maze/Images/powderEx.PNG", new Point(610, 140)));
        items.add(new InventoryItem("foam", "Maze/Images/foamEx.PNG", new Point(610, 280)));
        items.add(new InventoryItem("co2", "Maze/Images/co2Ex.PNG", new Point(610, 430)));
        hazards.add(new Hazard("water", "Maze/Images/fire.png", new Point(93, 140)));        // A
        hazards.add(new Hazard("powder", "Maze/Images/gas.PNG", new Point(184, 461)));   // B
        hazards.add(new Hazard("foam", "Maze/Images/liquid.PNG", new Point(277, 277)));     // C
        hazards.add(new Hazard("co2", "Maze/Images/electric.PNG", new Point(370, 370)));       // D
        hazards.add(new Hazard("water", "Maze/Images/fire.png", new Point(415, 507)));       // E
        hazards.add(new Hazard("water", "Maze/Images/fire.png", new Point(508, 277)));       // F
        hazards.add(new Hazard("water", "Maze/Images/fire.png", new Point(510, 47)));        // G

        InputMap keyBoardInput = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        keyBoardInput.clear();
        getActionMap().clear();
        keyBoardInput.put(KeyStroke.getKeyStroke('w'), "up");
        keyBoardInput.put(KeyStroke.getKeyStroke('a'), "left");
        keyBoardInput.put(KeyStroke.getKeyStroke('s'), "down");
        keyBoardInput.put(KeyStroke.getKeyStroke('d'), "right");
        keyBoardInput.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
        keyBoardInput.put(KeyStroke.getKeyStroke("ENTER"), "interact");
        keyBoardInput.put(KeyStroke.getKeyStroke('1'), "inven1");
        keyBoardInput.put(KeyStroke.getKeyStroke('2'), "inven2");
        keyBoardInput.put(KeyStroke.getKeyStroke('3'), "inven3");
        keyBoardInput.put(KeyStroke.getKeyStroke('4'), "inven4");
        getActionMap().put("up", movePlayer(1));
        getActionMap().put("left", movePlayer(2));
        getActionMap().put("down", movePlayer(3));
        getActionMap().put("right", movePlayer(4));
        getActionMap().put("inven1", selectItem(0));
        getActionMap().put("inven2", selectItem(1));
        getActionMap().put("inven3", selectItem(2));
        getActionMap().put("inven4", selectItem(3));
        getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseOpen = !pauseOpen;
                selectedInstructions = false;
                pauseMenu.selected = 0;
                repaint();
            }
        });
        getActionMap().put("interact", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWon||life==0) gamedone = true;
                if (pauseOpen) {
                    if (pauseMenu.selected == 0) {   // resume game
                        pauseOpen = false;
                    } else if (pauseMenu.selected == 1) {    // instructions
                        selectedInstructions = !selectedInstructions;
                    } else if (pauseMenu.selected == 2) {    // exit
                        wantsToQuit = true;
                        gamedone = true;
                    }
                } else {
                    if (checkCoords(character.x + 1, character.y) && map.map[character.x + 1][character.y] != 'Y' && map.map[character.x + 1][character.y] != 'X') {
                        useItem(map.map[character.x + 1][character.y], character.x + 1, character.y);
                    } else if (checkCoords(character.x - 1, character.y) && map.map[character.x - 1][character.y] != 'Y' && map.map[character.x - 1][character.y] != 'X') {
                        useItem(map.map[character.x - 1][character.y], character.x - 1, character.y);
                    } else if (checkCoords(character.x, character.y - 1) && map.map[character.x][character.y - 1] != 'Y' && map.map[character.x][character.y - 1] != 'X') {
                        useItem(map.map[character.x][character.y - 1], character.x, character.y - 1);
                    } else if (checkCoords(character.x, character.y + 1) && map.map[character.x][character.y + 1] != 'Y' && map.map[character.x][character.y + 1] != 'X') {
                        useItem(map.map[character.x][character.y + 1], character.x, character.y + 1);
                    }

                }
                repaint();
            }
        });
        displayRules();
    }

    /**
     * This method selects the item the Player is holding in their hand.
     *
     * @param index - The index of the item in the InventoryItem array.
     * @return - The AbstractAction performed when this method was called.
     */
    private AbstractAction selectItem(int index) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pauseOpen) {
                    currentItem = index;
                    repaint();
                }
            }
        };
    }

    /**
     * This method moves the player according to the corresponding arrow direction.
     *
     * @param arrow - Indicates the direction the player wants to move in.
     * @return - The AbstractAction performed when the player attempts to move.
     */
    private AbstractAction movePlayer(int arrow) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pauseOpen) {
                    if (arrow == 1) {        // up
                        if (checkCoords(character.x, character.y - 1)) {
                            if (map.map[character.x][character.y - 1] == 'Y') {
                                character.y -= 1;
                            }
                        }
                    } else if (arrow == 2) {  // left
                        if (checkCoords(character.x - 1, character.y)) {
                            if (map.map[character.x - 1][character.y] == 'Y') {
                                character.x -= 1;
                            }
                        }
                    } else if (arrow == 3) {  // down
                        if (checkCoords(character.x, character.y + 1)) {
                            if (map.map[character.x][character.y + 1] == 'Y') {
                                character.y += 1;
                            }
                        }
                    } else if (arrow == 4) {  // right
                        if (checkCoords(character.x + 1, character.y)) {
                            if (map.map[character.x + 1][character.y] == 'Y') {
                                character.x += 1;
                            }
                        }
                    }
                    if (map.map[character.x][character.y] == 'E') isWon = true;
                } else if (pauseOpen) {
                    if (arrow == 1) {
                        if (pauseMenu.selected > 0)
                            pauseMenu.selected -= 1;
                    } else if (arrow == 3) {
                        if (pauseMenu.selected < 2)
                            pauseMenu.selected += 1;
                    }
                }
                repaint();
            }
        };
    }

    @Override
    public void paintComponent(Graphics g) {
        DefeatTheHeat.graphics = (Graphics2D) g;
        if (!pauseOpen) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(map.background, 0, 0, null);
            for (int i = 0; i < items.size(); i++) {
                if (currentItem == i) {
                    items.get(i).enlarge();
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Serif", Font.BOLD, 16));
                    if (i == 0) {
                        g.drawString("1-WATER", 700, 20);
                        g.drawString("EXTINGUISHER", 670, 40);
                        g.drawString("For wood, cloth,", 685, 80);
                        g.drawString("coal, plastics, and", 675, 100);
                        g.drawString("other solid fires.", 680, 120);
                    } else if (i == 1) {
                        g.drawString("2-POWDER", 700, 170);
                        g.drawString("EXTINGUISHER", 670, 190);
                        g.drawString("For gas fires.", 685, 230);
                    } else if (i == 2) {
                        g.drawString("3-FOAM", 700, 320);
                        g.drawString("EXTINGUISHER", 670, 340);
                        g.drawString("For liquid fires.", 685, 380);
                    } else if (i == 3) {
                        g.drawString("4-Carbon Dioxide", 670, 470);
                        g.drawString("EXTINGUISHER", 670, 490);
                        g.drawString("For electrical", 685, 530);
                        g.drawString("fires.", 700, 550);
                    }
                } else {
                    items.get(i).draw();
                }
            }
            for (int i = 0; i < hazards.size(); i++) {
                if (i > count - 1) {
                    hazards.get(i).draw();
                }
            }
            for (int i = 0; i < life; i++) {
                g.drawImage(character.hp, i * 25, 530, null);
            }
            g.drawImage(character.sprite, (int) (600 / 13.0 * character.x + 2), (int) (600 / 13.0 * character.y + 2), null);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            if (selectedInstructions) {
                g.drawImage(DefeatTheHeat.imageFromFile("Maze/Images/Instructions_3.png"), 175, 10, null);
            } else {
                for (int i = 0; i < pauseMenu.options.length; i++) {
                    if (i == pauseMenu.selected) {
                        pauseMenu.options[i].enlarge();
                    } else
                        pauseMenu.options[i].draw();
                }
            }
        }
        if (isWon){
            g.drawImage(DefeatTheHeat.imageFromFile("Maze/Images/win.png"), 0, 0, null);
            g.drawImage(DefeatTheHeat.imageFromFile("Menu/continueGame.png"), 300, 200, null);
        } else if (life==0){
            g.drawImage(DefeatTheHeat.imageFromFile("Maze/Images/lose.png"), 0, 0, null);
            g.drawImage(DefeatTheHeat.imageFromFile("Menu/continueGame.png"), 300, 200, null);
        }
    }

    /**
     * Plays the game / continues to check for keyboard input until the game exit conditions are met.
     */
    public void play() {
        revalidate();
        repaint();
        while (!wantsToQuit && !isWon) {
            if (life == 0) {
                isWon = false;
                break;
            }
            if (character.x == 11 && character.y == 1) {
                isWon = true;
                break;
            }
        }
        repaint();
        while(!gamedone);
        repaint();
        getActionMap().clear();
    }

    /**
     * This method checks whether the coordinates that are being looked at are actually on the board, to avoid a nullpointerexception.
     *
     * @param x - The column number/ horiz position
     * @param y - The row number/ vert position
     * @return - true if in bounds, else false.
     */
    private boolean checkCoords(int x, int y) {
        return (x > -1 && x < 13 && y > -1 && y < 13);
    }

    /**
     * This method is called when an item is attempted to be used. It either takes away lives or
     * allows the user to move to the coordinate where the hazard previously was.
     *
     * @param cur - The character at the location of the hazard on the map grid.
     * @param x   - The x coordinate of the location we are looking at.
     * @param y   - The y coordinate of the location we are looking at.
     */
    private void useItem(char cur, int x, int y) {
        if (items.get(currentItem).getId().equals(hazards.get(cur - 65).getId())) {
            map.map[x][y] = 'Y';
            count++;
        } else {
            life--;
        }
    }

    /**
     * This method displays the rules for level one in a JDialog.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> rulesLabel </b> The JLabel with explaining the rules for level one.
     */
    public void displayRules() {
        rulesDialog = new JDialog(DefeatTheHeat.entireFrame);
        JLabel rulesLabel = new JLabel(new ImageIcon(DefeatTheHeat.class.getClassLoader().getResource("Maze/Images/Instructions_3.png")));

        rulesLabel.setVerticalAlignment(JLabel.CENTER);
        rulesLabel.setHorizontalAlignment(JLabel.CENTER);
        rulesDialog.setSize(480, 564);
        rulesDialog.setLocationRelativeTo(DefeatTheHeat.entireFrame);
        rulesDialog.add(rulesLabel);
        rulesDialog.setVisible(true);
        rulesDialog.requestFocus();
        rulesDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
