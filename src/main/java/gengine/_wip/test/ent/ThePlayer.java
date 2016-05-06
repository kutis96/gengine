package gengine._wip.test.ent;

import gengine.events.receivers.KeyboardEventReceiver;
import gengine.logic.ControllerFacade;
import gengine.util.coords.*;
import gengine.world.entity.*;
import gengine.world.entity.inventory.items.Weapon;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ThePlayer extends PlayerEntity implements KeyboardEventReceiver {

    private static final Logger LOG = Logger.getLogger(ThePlayer.class.getName());

    private final Image img;
    private final ControllerFacade cf;

    public ThePlayer(ControllerFacade facade) throws IOException {
        super(facade, 20);
        this.cf = facade;
        this.img = ImageIO.read(new File("/home/rkutina/testimages/redball.png"));
    }

    @Override
    public int getState() {
        return TiledWorldEntity.STATE_UNDEFINED;
    }

    @Override
    public void resetState() {
        //Do nothing whatsoever
    }

    @Override
    public void tick(long dt) {
        this.advancePos(new Coords3D());
    }

    @Override
    public Image render() {
        return this.img;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        LOG.info("HELLO");
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    LOG.info("UP!");
                    move(new Coords3D(0, -1, 0));
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    LOG.info("DOWN!");
                    move(new Coords3D(0, 1, 0));
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    LOG.info("LEFT!");
                    move(new Coords3D(-1, 0, 0));
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    LOG.info("RIGHT!");
                    move(new Coords3D(1, 0, 0));
                    break;
                }
                default:
                    break;  //do nothing
            }
        } catch (ValueException ve) {
            LOG.log(Level.SEVERE, "Found a ValueException where there definitely shouldn't be one.", ve);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Not required
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Not required
    }

    @Override
    public boolean mouseHit(Point point) {
        return false;
    }
    
    private void move(Coords3D direction){
        this.advancePos(direction, true, true);
        cf.changeCamPosition(this.getPos());
    }

    @Override
    public void receiveAttack(Weapon w, NPCEntity perpetrator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
