package gengine.world.tile.tiles;

import gengine.world.tile.Tile;
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

}
