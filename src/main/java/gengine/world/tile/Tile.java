package gengine.world.tile;

import gengine.iwishjavahadtraits.Renderable;
import gengine.world.TiledWorld;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Tile extends Renderable {
    
    public void tick(TiledWorld iw, long dt);
}
