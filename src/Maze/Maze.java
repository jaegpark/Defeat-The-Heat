package Maze;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * This is the main game panel for the third level
 *
 * @author Jae Park
 */
public class Maze extends JPanel {

    private boolean wantsToQuit;
    private boolean pauseOpen;
    private Inventory items;
    private int currentItem;
    private Board map;
    private Player character;

    private Maze(){
        wantsToQuit = false;
        items = new Inventory();
        currentItem = 0;
        InputMap keyBoardInput =getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        keyBoardInput.put(KeyStroke.getKeyStroke("W"), "up");
        keyBoardInput.put(KeyStroke.getKeyStroke("A"), "left");
        keyBoardInput.put(KeyStroke.getKeyStroke("S"), "down");
        keyBoardInput.put(KeyStroke.getKeyStroke("D"), "right");
        keyBoardInput.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
        keyBoardInput.put(KeyStroke.getKeyStroke("ENTER"), "interact");
        keyBoardInput.put(KeyStroke.getKeyStroke("1"), "inven1");
        keyBoardInput.put(KeyStroke.getKeyStroke("2"), "inven2");
        keyBoardInput.put(KeyStroke.getKeyStroke("3"), "inven3");
        keyBoardInput.put(KeyStroke.getKeyStroke("4"), "inven4");
        keyBoardInput.put(KeyStroke.getKeyStroke("5"), "inven5");
        keyBoardInput.put(KeyStroke.getKeyStroke("6"), "inven6");
        keyBoardInput.put(KeyStroke.getKeyStroke("7"), "inven7");
        getActionMap().put("up", movePlayer(1));
        getActionMap().put("left", movePlayer(2));
        getActionMap().put("down", movePlayer(3));
        getActionMap().put("right", movePlayer(4));
        getActionMap().put("inven1", selectItem(0));
        getActionMap().put("inven2", selectItem(1));
        getActionMap().put("inven3", selectItem(2));
        getActionMap().put("inven4", selectItem(3));
        getActionMap().put("inven5", selectItem(4));
        getActionMap().put("inven6", selectItem(5));
        getActionMap().put("inven7", selectItem(6));
        getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseOpen = !pauseOpen;
            }
        });
        getActionMap().put("interact", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseOpen){

                }else{

                }
            }
        });
    }

    private AbstractAction selectItem(int index){
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pauseOpen){
                    currentItem = index;
                    items.select(index);
                    repaint();
                }
            }
        };
    }
    private AbstractAction movePlayer(int arrow){
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pauseOpen){
                    if (arrow == 1){        // up
                        if (map.isMovableTo[character.x][character.y-1]){
                            character.y -=1;
                            repaint();
                        }
                    }else if (arrow == 2){  // down
                        if (map.isMovableTo[character.x][character.y+1]){
                            character.y +=1;
                            repaint();
                        }
                    }else if (arrow == 3){  // left
                        if (map.isMovableTo[character.x-1][character.y]){
                            character.x -=1;
                            repaint();
                        }
                    }else if (arrow == 4){  // right
                        if (map.isMovableTo[character.x+1][character.y]){
                            character.x +=1;
                            repaint();
                        }
                    }
                }else if (pauseOpen){
                    if (arrow == 1){

                    }else if (arrow == 2){

                    }else if (arrow == 3){

                    }else if (arrow == 4){

                    }
                }
            }
        };
    }

    @Override
    public void paintComponent(Graphics g){
        DefeatTheHeat.graphics =(Graphics2D) g;

    }

    public void play(){
        revalidate();
        repaint();
        while(!wantsToQuit){
            revalidate();
            repaint();
        }
    }


}
