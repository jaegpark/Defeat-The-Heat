package Maze;

import Main.DefeatTheHeat;

import java.awt.*;

public class Inventory {

    private int selected;
    private InventoryItem[] items;
    private Image [] itemImages;

    public Inventory() {
        items = new InventoryItem[7];
        // TODO: initialize images and item values
        for (int i = 0; i < items.length; i++) {

        }
    }

    public void select(int index) {
        selected = index;
        items[selected].enlarge();
    }


}
