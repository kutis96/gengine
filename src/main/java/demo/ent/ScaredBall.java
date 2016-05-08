package demo.ent;

import gengine.events.receivers.MouseEventReceiver;
import gengine.events.receivers.ProximityEventReceiver;
import gengine.logic.facade.WorldControllerFacade;
import gengine.rendering.overlay.*;
import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
import gengine.world.entity.WorldEntity;
import gengine.world.entity.TiledWorldEntity;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ScaredBall extends TiledWorldEntity implements ProximityEventReceiver, MouseEventReceiver, HasOverlays {

    private static final Logger LOG = Logger.getLogger(ScaredBall.class.getName());

    private final Image img_scared;
    private final Image img_normal;

    private TextOverlay olay;

    private static final int STATE_SCARED = 1;
    private static final int STATE_NORMAL = 2;
    private volatile int state;
    private volatile long lastScared = 0;
    private long lastMoved = 0;

    public ScaredBall(WorldControllerFacade facade) throws IOException {
        super(facade);

        this.img_scared = ImageIO.read(this.getClass().getResource("/demo/entity/greenball.png"));
        this.img_normal = ImageIO.read(this.getClass().getResource("/demo/entity/blueball.png"));

        this.olay = new TextOverlay("", new Coords3D(0, 0, 9000));
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
            lastMoved += dt;

            if (lastMoved > 10) {
                try {
                    this.advancePos(new Coords3D(
                            (float) (1 * (Math.random() - 0.5)),
                            (float) (1 * (Math.random() - 0.5)),
                            0), true);
                } catch (ValueException ex) {
                    LOG.log(Level.SEVERE, "This is bad", ex);
                }
                lastMoved = 0;
            }

            if (lastScared > 1000) {
                state = STATE_NORMAL;

                this.olay.setText("Whew.");
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

        this.olay.setText(" Aaaah!"
                + "\nRun away!");
        this.state = STATE_SCARED;

        this.lastMoved = 0;
        this.lastScared = 0;
    }

    @Override
    public float getProxDistance() {
        return (float) 1.5;
    }

    @Override
    public boolean mouseHit(Point point) {
        if (Point.distance(point.x, point.y, 0, 0) <= this.img_normal.getHeight(null) / 2) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.state = STATE_SCARED;
        this.olay.setText("IT CLICKED ME!");

        this.lastMoved = 0;
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
