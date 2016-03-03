package gengine.world.tile;

import gengine.iwishjavahadtraits.Renderable;
import gengine.world.TiledWorld;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Tile implements Renderable {
    
    public void tick(TiledWorld iw, long dt){
        //TODO: add logger here
    }
}
