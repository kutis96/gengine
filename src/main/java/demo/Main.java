package demo;

import gengine.logic.controller.MainController;
import gengine.util.UnsupportedFormatException;
import java.awt.*;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * The Main.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Main {

    public static void main(String[] args) throws IOException, UnsupportedFormatException {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("GEngine");
        mainFrame.setSize(new Dimension(1024, 768));
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MainController mc;
        mc = new MainDemoController(mainFrame);
        
        Thread main = new Thread(mc);
        
        main.start();
    }
}
