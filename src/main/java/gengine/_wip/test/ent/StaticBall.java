package gengine._wip.test.ent;

import gengine.world.WorldFacade;
import gengine.world.entity.TiledWorldEntity;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class StaticBall extends TiledWorldEntity {

    private final Image img;

    public StaticBall(WorldFacade facade) throws IOException {
        super(facade);
        this.img = ImageIO.read(new File("/home/rkutina/testimages/blueball.png"));
    }

    @Override
    public int getState() {
        return TiledWorldEntity.STATE_UNDEFINED;
    }

    @Override
    public void resetState() {
        //do nothing
    }

    @Override
    public void tick(long dt) {
        //Do nothing, just sit there
    }

    @Override
    public Image render() {
        return this.img;
    }

    @Override
    public boolean mouseHit(Point point) {
        return false;
    }

}
