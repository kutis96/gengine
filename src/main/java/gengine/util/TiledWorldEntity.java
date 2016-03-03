package gengine.util;

import gengine.util.coords.Coords3D;
import gengine.tile.TiledWorld;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface TiledWorldEntity extends Renderable {
    Coords3D getPos();
    void tick(TiledWorld iw, long dt);
}
