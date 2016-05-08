package gengine.logic.view;

import gengine.logic.callback.Callback;
import gengine.util.Worker;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ColorView extends View {

    public ColorView() {
        
    }

    @Override
    public void initialize(Callback onInit) {

    }

    @Override
    public void deconstruct(Callback onCompletion) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.red);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

}
