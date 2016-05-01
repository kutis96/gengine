package gengine._wip.test.ent;

import gengine.world.entity.WorldEntity;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Ball extends WorldEntity {

    private final Image img;

    public Ball() throws IOException {
        this.img = ImageIO.read(new File("/home/rkutina/testimages/blueball.png"));
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

}
