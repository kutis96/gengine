package demo.ent;

import gengine.events.receivers.MouseEventReceiver;
import gengine.events.receivers.ProximityEventReceiver;
import gengine.logic.ControllerFacade;
import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
import gengine.world.entity.WorldEntity;
import gengine.world.entity.TiledWorldEntity;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
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
public class ScaredBall extends TiledWorldEntity implements ProximityEventReceiver, MouseEventReceiver {

    private static final Logger LOG = Logger.getLogger(ScaredBall.class.getName());

    private final Image img_scared;
    private final Image img_normal;

    private static final int STATE_SCARED = 1;
    private static final int STATE_NORMAL = 2;
    private volatile int state;
    private volatile long lastScared = 0;

    public ScaredBall(ControllerFacade facade) throws IOException {
        super(facade);

        this.img_scared = ImageIO.read(new File("/home/rkutina/testimages/greenball.png"));
        this.img_normal = ImageIO.read(new File("/home/rkutina/testimages/blueball.png"));

    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void resetState() {
        this.state = STATE_NORMAL;
    }

    @Override
    public void tick(long dt) {
        if (state == STATE_SCARED) {
            lastScared += dt;

            try {
                this.advancePos(new Coords3D(
                        (float) (2 * (Math.random() - 0.5)),
                        (float) (2 * (Math.random() - 0.5)), 
                        0), true);
            } catch (ValueException ex) {
                LOG.log(Level.SEVERE, "This is bad", ex);
            }

            if (lastScared > 1000) {
                state = STATE_NORMAL;
            }
        }
    }

    @Override
    public Image render() {
        switch (state) {
            case STATE_SCARED:
                return img_scared;
            default:
            case STATE_NORMAL:
                return img_normal;
        }
    }

    @Override
    public void onProxEvent(WorldEntity closeEntity, float distance) {

        this.state = STATE_SCARED;
        this.lastScared = 0;
    }

    @Override
    public float getProxDistance() {
        return (float) 1.5;
    }

    @Override
    public boolean mouseHit(Point point) {
        if (Point.distance(point.x, point.y, 0, 0) <= this.img_normal.getHeight(null) / 2) {
            LOG.info("" + point);
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.state = STATE_SCARED;
        this.lastScared = 200;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Not really required
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Not used
    }

}
