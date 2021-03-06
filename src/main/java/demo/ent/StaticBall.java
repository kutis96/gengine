package demo.ent;

import gengine.logic.facade.WorldControllerFacade;
import gengine.world.entity.TiledWorldEntity;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class StaticBall extends TiledWorldEntity {

    private final Image img;

    public StaticBall(WorldControllerFacade facade) throws IOException {
        super(facade);
        this.img = ImageIO.read(this.getClass().getResource("/demo/entity/blueball.png"));
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
