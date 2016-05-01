package gengine.events.receivers;

import java.awt.event.*;

/**
 * A mouse event receiver thing. Basically just a rebranded MouseListener.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface MouseEventReceiver extends MouseListener {

    @Override
    public void mouseClicked(MouseEvent e);

    @Override
    public void mousePressed(MouseEvent e);

    
    public void mouseDragged(MouseEvent e);

    @Override
    public void mouseReleased(MouseEvent e);

    @Override
    public void mouseEntered(MouseEvent e);

    @Override
    public void mouseExited(MouseEvent e);

}
