package Maze;

import Main.DefeatTheHeat;
import java.awt.*;


public class InventoryItem {
    private Image icon;
    private String match;
    public Point coordinates;

    public InventoryItem(String s, String path){
        match = s;
        icon = DefeatTheHeat.imageFromFile(path);
    }

    public void draw(){
        DefeatTheHeat.graphics.drawImage(icon, coordinates.x,coordinates.y,null);
    }

    /**
     *  Enlarges the image of the menu option when the user hovers over it.
     */
    void enlarge(){
        Dimension oldSize = new Dimension(icon.getWidth(null), icon.getHeight(null));
        Dimension newSize = new Dimension((int)(icon.getWidth(null)*1.1), (int)(icon.getHeight(null)*1.1));
        DefeatTheHeat.graphics.drawImage(icon, coordinates.x - (newSize.width - oldSize.width)/2, coordinates.y - (newSize.height - oldSize.height)/2, newSize.width, newSize.height,null);
    }

}
