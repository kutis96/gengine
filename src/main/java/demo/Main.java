package demo;

import java.awt.*;
import javax.swing.JFrame;

/**
 * The Main.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Main {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("GEngine");
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
