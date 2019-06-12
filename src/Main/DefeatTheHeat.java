package Main;

import Game.*;
import Menu.*;
import Menu.SplashScreen;
import Maze.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * The driver class for the program.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Jae Park
 * @version 6.0
 * <p>
 * 1
 * Created basic framework, with all variables and method headers.
 * 2
 * Created the initFrame method, and setup basic game loop in the main method.
 * 3
 * Created the imageFromFile method to reduce try-catch blocks in other classes, and to simplify code.
 * 4
 * Created the main game loop with proper save conditions, added File I/O for the saved games.
 * 5
 * Fixed the game loop so that it alters the contentPane instead of the actual panel on the JFrame.
 * This was done to have compatibility with the level 1 and level 2 classes.
 * Also fixed the navigation menu, so that the user would actually exit back to the main menu when they
 * wish to in between the game levels.
 * 6
 * Took out thingOnFrame variable since it wasn't necessary.
 * Also ensured that the keyboard input stopped after menu navigation.
 */
public class DefeatTheHeat {
    /**
     * The JFrame that everything is added to.
     */
    public static JFrame entireFrame;
    /**
     * The Graphics2D that is being drawn onto by other classes.
     */
    public static Graphics2D graphics;

    /**
     * This method returns an image from a passed in file path. This was created here and reused in other packages
     * to reduce the number of trycatch statements, and to have easier debugging.
     *
     * @param path - The filepath of the image we are looking for.
     * @return - Returns the image that was found, if the trycatch was successful. Else returns null.
     */
    public static BufferedImage imageFromFile(String path) {
        try {
            URL resource = DefeatTheHeat.class.getClassLoader().getResource(path);
            if (resource == null) throw new NullPointerException();
            return ImageIO.read(resource);
        } catch (IOException e) {
            System.err.println("There was an error retrieving " + path);
            e.printStackTrace();
        } catch (NullPointerException ae) {
            System.err.println(path);
            System.err.println(DefeatTheHeat.class.getClassLoader().getResource(path));
            ae.printStackTrace();
        }
        return null;
    }

    /**
     * Initializes the JFrame, with proper size, position, values, and exit conditions.
     */
    public static void initFrame() {
        entireFrame = new JFrame();
        entireFrame.getContentPane().setPreferredSize(new Dimension(800, 600));
        entireFrame.setResizable(false);
        entireFrame.pack();
        entireFrame.setLocationRelativeTo(null);
        entireFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        entireFrame.setVisible(true);
        entireFrame.requestFocus();
    }

    /**
     * The main method that drives the program, and contains the game loop.
     *
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DefeatTheHeat::initFrame);
        LevelLoader saves = new LevelLoader(25);
        SplashScreen s = new SplashScreen();
        entireFrame.add(s);
        s.play();
        entireFrame.remove(s);
        // MAIN GAME LOOP
        while (entireFrame.isDisplayable()) {
            entireFrame.getContentPane().removeAll();   // clears the content on the screen
            MainMenu menu = new MainMenu();             // creates a new MainMenu that is to be added
            entireFrame.setContentPane(menu);       // set the current content pane as the main menu
            int option = menu.getOption();          // retrieve the main menu option
            entireFrame.getContentPane().remove(menu);
            switch (option) {
                case 0: // new game
                    String name = JOptionPane.showInputDialog(entireFrame, "Please enter your name");
                    if (name != null && !name.equals("")) {
                        LevelOne a = new LevelOne(entireFrame);
                        ContinueMenu menu1 = new ContinueMenu();
                        a.getEndDialog().add(menu1);
                        menu1.setSize(510, 400);
                        menu1.setLocation(0, 0);
                        int continueOption1 = menu1.getOption();
                        a.eraseComponents();
                        if (a.noHazardsLeft()) {     // save their progress if they completed level 1
                            saves.saveProgress(name, 1);
                        }
                        if (continueOption1 == 1) {           // if they chose to exit, break the switch
                            break;
                        } else {                    // only let them continue to next game if they completed level 2
                            if (!a.noHazardsLeft()) {
                                break;
                            }
                        }
                        LevelTwo b = new LevelTwo(entireFrame);
                        ContinueMenu menu2 = new ContinueMenu();
                        b.getEndDialog().add(menu2);
                        menu2.setSize(510, 400);
                        menu2.setLocation(0, 0);
                        int continueOption2 = menu2.getOption();
                        b.eraseComponents();
                        if (b.checkGame) {
                            saves.saveProgress(name, 2);
                        }
                        if (continueOption2 == 1) {
                            break;
                        } else {
                            if (!b.checkGame) {
                                break;
                            }
                        }
                        entireFrame.getContentPane().setSize(new Dimension(800, 600));
                        Maze maze = new Maze();
                        entireFrame.setContentPane(maze);
                        entireFrame.setVisible(true);
                        maze.play();
                        if (maze.isWon) {
                            saves.saveProgress(name, 3);
                        } else saves.saveProgress(name, 2);
                    }
                    break;
                case 1: // continue game
                    String lookfor = JOptionPane.showInputDialog(entireFrame, "Please enter your name");
                    if (lookfor != null && !lookfor.equals("")) {
                        int level = saves.getPlayerData(lookfor);
                        if (level == 1) {
                            entireFrame.getContentPane().remove(menu);
                            entireFrame.revalidate();
                            LevelTwo b = new LevelTwo(entireFrame);
                            ContinueMenu menu2 = new ContinueMenu();
                            b.getEndDialog().add(menu2);
                            menu2.setSize(510, 400);
                            menu2.setLocation(0, 0);
                            int continueOption2 = menu2.getOption();
                            b.eraseComponents();
                            if (b.checkGame) {
                                saves.saveProgress(lookfor, 2);
                            }
                            if (continueOption2 == 1) {
                                break;
                            } else {
                                if (!b.checkGame) {
                                    break;
                                }
                            }
                            entireFrame.getContentPane().setSize(new Dimension(800, 600));
                            Maze maze = new Maze();
                            entireFrame.setContentPane(maze);
                            entireFrame.setVisible(true);
                            maze.play();
                            if (maze.isWon) {
                                saves.saveProgress(lookfor, 3);
                            } else saves.saveProgress(lookfor, 2);
                        } else if (level == 2) {
                            entireFrame.getContentPane().setPreferredSize(new Dimension(800, 600));
                            entireFrame.revalidate();
                            Maze maze = new Maze();
                            entireFrame.setContentPane(maze);
                            entireFrame.setVisible(true);
                            maze.play();
                            if (maze.isWon) {
                                saves.saveProgress(lookfor, 3);
                            } else saves.saveProgress(lookfor, 2);
                        } else if (level == 3 || level == -1) {
                            entireFrame.getContentPane().remove(menu);
                            LevelOne a = new LevelOne(entireFrame);
                            ContinueMenu menu1 = new ContinueMenu();
                            a.getEndDialog().add(menu1);
                            menu1.setSize(510, 400);
                            menu1.setLocation(0, 0);
                            int continueOption1 = menu1.getOption();
                            a.eraseComponents();
                            if (a.noHazardsLeft()) {     // save their progress if they completed level 1
                                saves.saveProgress(lookfor, 1);
                            }
                            if (continueOption1 == 1) {           // if they chose to exit, break the switch
                                break;
                            } else {                    // only let them continue to next game if they completed level 2
                                if (!a.noHazardsLeft()) {
                                    break;
                                }
                            }
                            LevelTwo b = new LevelTwo(entireFrame);
                            ContinueMenu menu2 = new ContinueMenu();
                            b.getEndDialog().add(menu2);
                            menu2.setSize(510, 400);
                            menu2.setLocation(0, 0);
                            int continueOption2 = menu2.getOption();
                            b.eraseComponents();
                            if (b.checkGame) {
                                saves.saveProgress(lookfor, 2);
                            }
                            if (continueOption2 == 1) {
                                break;
                            } else {
                                if (!b.checkGame) {
                                    break;
                                }
                            }
                            entireFrame.getContentPane().setSize(new Dimension(800, 600));
                            Maze maze = new Maze();
                            entireFrame.setContentPane(maze);
                            entireFrame.setVisible(true);
                            maze.play();
                            if (maze.isWon) {
                                saves.saveProgress(lookfor, 3);
                            } else saves.saveProgress(lookfor, 2);
                        }
                    }
                    break;
                case 2: // exit
                    entireFrame.setVisible(false);
                    entireFrame.dispose();
                    System.exit(0);
                    break;
            }
        }
    }
}
