package gengine.world.tile.tiles;

import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
import java.awt.Image;

/**
 * A tile designed not to be drawn.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class NullTile extends Tile {

    @Override
    public void tick(long dt) {
        //do nothing whatsoever
    }

    @Override
    public Image render() {
        return null;
    }

    @Override
    public boolean canSeeThrough(Coords3D direction, Coords3D offset) {
        //null tiles are always see-through. it's basically just 'air' or 'vacuum' of the world.
        return true;
    }

    @Override
    public boolean isWall() {
        return false;
    }

}
