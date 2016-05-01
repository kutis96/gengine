package gengine._wip.test.ent;

import gengine.events.receivers.ProximityEventReceiver;
import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
import gengine.world.entity.WorldEntity;
import java.awt.Image;
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
public class BallThing extends WorldEntity implements ProximityEventReceiver {

    private static final Logger LOG = Logger.getLogger(BallThing.class.getName());

    private final Image img;

    public BallThing() throws IOException {
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
    public void onProxEvent(WorldEntity closeEntity, float distance) {
        try {
            LOG.info("FOUND AN ENTITY CLOSE TO ME! HELP!");
            this.advancePos(new Coords3D((float) Math.random(), (float) Math.random(), (float) Math.random()));
        } catch (ValueException ex) {
            LOG.severe("ValueException found! D: >");
        }

    }

    @Override
    public float getProxDistance() {
        return 3;
    }

}
