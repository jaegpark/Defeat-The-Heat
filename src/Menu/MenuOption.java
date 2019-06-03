package Menu;

import Main.DefeatTheHeat;

import java.awt.*;

public class MenuOption {

    /** The Point that holds the position of the MenuOption */
    public Point position;
    /** The Image that represents this menu option to the user */
    public Image image;

    /**
     *  The constructor, that creates a new MenuOption given an image and location.
     *
     * @param path - The file path of the image
     * @param location - The location on the screen where the option is displayed
     */
    public MenuOption(String path, Point location){
        image = DefeatTheHeat.imageFromFile(path);
        this.position = location;
    }

    /**
     *  Draws the current menu option at the specified point coordinates
     */
    public void draw(){
        DefeatTheHeat.graphics.drawImage(image, position.x, position.y, null);
    }

    /**
     *  Enlarges the image of the menu option when the user hovers over it.
     */
    void enlarge(){
        Dimension oldSize = new Dimension(image.getWidth(null), image.getHeight(null));
        Dimension newSize = new Dimension((int)(image.getWidth(null)*1.1), (int)(image.getHeight(null)*1.1));
        DefeatTheHeat.graphics.drawImage(image, position.x - (newSize.width - oldSize.width)/2, position.y - (newSize.height - oldSize.height)/2, newSize.width, newSize.height,null);
    }


}
