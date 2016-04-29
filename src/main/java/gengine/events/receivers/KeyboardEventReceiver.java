package gengine.events.receivers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A keyboard event receiver thing. Basically just a rebranded KeyListener.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface KeyboardEventReceiver extends KeyListener {

    @Override
    public void keyPressed(KeyEvent e);

    @Override
    public void keyReleased(KeyEvent e);

    @Override
    public void keyTyped(KeyEvent e);

}
