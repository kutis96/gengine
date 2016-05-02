package gengine._wip.test.ent;

import gengine.events.receivers.KeyboardEventReceiver;
import gengine.util.coords.*;
import gengine.world.WorldFacade;
import gengine.world.entity.TiledWorldEntity;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ThePlayer extends TiledWorldEntity implements KeyboardEventReceiver {
    
    private static final Logger LOG = Logger.getLogger(ThePlayer.class.getName());
    
    private final Image img;
    
    public ThePlayer(WorldFacade facade) throws IOException {
        super(facade);
        this.img = ImageIO.read(new File("/home/rkutina/testimages/redball.png"));
    }
    
    @Override
    public int getState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void resetState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void tick(long dt) {
        //Do nothing
    }
    
    @Override
    public Image render() {
        return this.img;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    LOG.info("UP!");
                    this.advancePos(new Coords3D(0, -1, 0));
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    LOG.info("DOWN!");
                    this.advancePos(new Coords3D(0, 1, 0));
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    LOG.info("LEFT!");
                    this.advancePos(new Coords3D(-1, 0, 0));
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    LOG.info("RIGHT!");
                    this.advancePos(new Coords3D(1, 0, 0));
                    break;
                }
            }
        } catch (ValueException ve) {
            LOG.severe("Found a ValueException where there definitely shouldn't be one.");
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public boolean mouseHit(Point point) {
        return false;
    }
    
}