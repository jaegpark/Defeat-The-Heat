package Main;

import Game.*;
import Menu.*;
import Menu.SplashScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DefeatTheHeat {

    public static JPanel thingOnFrame;
    public static JFrame entireFrame;

    public static Graphics2D graphics;

    public static BufferedImage imageFromFile(String path) {
        try {
            URL resource = DefeatTheHeat.class.getClassLoader().getResource(path);
            if (resource == null) throw new NullPointerException();
            return ImageIO.read(resource);
        } catch (IOException | NullPointerException e) {
            System.err.println("There was an error retrieving " + path);
            //if (DEBUGGING) e.printStackTrace();
        }
        return null;
    }

    public static void initFrame(){
        entireFrame = new JFrame();
        entireFrame.getContentPane().setPreferredSize(new Dimension(800,600));
        entireFrame.setResizable(false);
        entireFrame.pack();
        entireFrame.setLocationRelativeTo(null);
        entireFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        entireFrame.setVisible(true);
        entireFrame.requestFocus();
    }

    public static void main (String args[])
    {
        SwingUtilities.invokeLater(DefeatTheHeat::initFrame);
        SplashScreen s = new SplashScreen();
        //entireFrame.add(s);
        //s.play();
        //thingOnFrame = s;
        // MAIN GAME LOOP
        while(entireFrame.isVisible()){
            //entireFrame.remove(thingOnFrame);
            MainMenu menu = new MainMenu();
            entireFrame.add(menu);
            int option = menu.getOption();
            switch (option){
                case 0:
                    // TODO: MAKE THE ROOM SELECTOR HERE
                    // TODO: MAKE THE HIGH SCORES
                    System.out.println(1);
                    LevelOne game1 = new LevelOne();
                    game1.main(new String[2]);
                    break;
                case 1:
                    System.out.println(2);
                    LevelTwo game2 = new LevelTwo();
                    game2.main(new String[2]);
                    break;
                case 2:
                    System.out.println(3);
                    entireFrame.setVisible(false);
                    entireFrame.dispose();
                    break;
            }
        }
    }
}
