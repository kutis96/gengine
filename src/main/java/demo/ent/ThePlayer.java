package demo.ent;

import gengine.events.receivers.KeyboardEventReceiver;
import gengine.logic.facade.WorldControllerFacade;
import gengine.rendering.overlay.*;
import gengine.util.neco.Neco3D;
import gengine.world.entity.*;
import gengine.world.entity.inventory.items.Weapon;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ThePlayer extends PlayerEntity implements KeyboardEventReceiver, HasOverlays {

    private static final Logger LOG = Logger.getLogger(ThePlayer.class.getName());

    private final TextOverlay olay
            = new TextOverlay("Player", new Neco3D(new int[]{0, 0, 100}, true));

    private final Image img;
    private final WorldControllerFacade cf;

    public ThePlayer(WorldControllerFacade facade) throws IOException {
        super(facade, 20);
        this.cf = facade;
        this.img = ImageIO.read(this.getClass().getResource("/demo/entity/redball.png"));
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
        this.advancePos(new Neco3D());
    }

    @Override
    public Image render() {
        return this.img;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                move(new Neco3D(new int[]{0, -1, 0}, true));
                break;
            }
            case KeyEvent.VK_DOWN: {
                move(new Neco3D(new int[]{0, 1, 0}, true));
                break;
            }
            case KeyEvent.VK_LEFT: {
                move(new Neco3D(new int[]{-1, 0, 0}, true));
                break;
            }
            case KeyEvent.VK_RIGHT: {
                move(new Neco3D(new int[]{1, 0, 0}, true));
                break;
            }
            default:
                break;  //do nothing
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

    private void move(Neco3D direction) {
        this.advancePos(direction, true, true);

        this.olay.setText("Player\n" + this.getPos());

        cf.changeCamPosition(this.getPos());
    }

    @Override
    public void receiveAttack(Weapon w, NPCEntity perpetrator) {
        LOG.log(Level.INFO, "OUCH, I GOT HIT BY {0} wielding {1}!", new Object[]{perpetrator, w});
    }

    @Override
    public Overlay[] getOverlays() {
        return new Overlay[]{this.olay};
    }

    @Override
    public void attachOverlay(Overlay overlay) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void animateOverlays(long dt) {
        //do nothing
    }
}
