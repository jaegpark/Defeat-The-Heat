package Maze;

import Main.DefeatTheHeat;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board  {

    public Dimension mapSize;

    public boolean [][] isMovableTo;

    public Board(String path){
        mapSize = new Dimension();
        Scanner sc = new Scanner(DefeatTheHeat.class.getClassLoader().getResourceAsStream(path));
        mapSize.width =sc.nextInt();
        mapSize.height = sc.nextInt();
        isMovableTo = new boolean[mapSize.width][mapSize.height];
        for(int i = 0; i< mapSize.height; i++){
            String temp = sc.nextLine();
            for (int j = 0; j < mapSize.width; j++) {
                if (temp.charAt(j)!='X'&& temp.charAt(j)!='O'){
                    isMovableTo[i][j] = true;
                }
                if (temp.charAt(j)=='S'){
                    // TODO: set the start coordinates
                }
                if (temp.charAt(j)=='E'){
                    // TODO: set the exit coordinates
                }
            }
        }
    }

}
