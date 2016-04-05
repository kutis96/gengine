package sillies;

import javax.swing.JFrame;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TestyWindow extends JFrame {
    @Override
    public void repaint() {
        super.repaint();
        System.out.println("REPAINT");
    }
}
