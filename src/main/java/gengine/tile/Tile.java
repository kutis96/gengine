package gengine.tile;

import gengine.util.Renderable;
import gengine.util.Visible;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Tile extends Visible implements Renderable {
    public void tick(TiledWorld iw, long dt){
        //TODO: add logger here
    }
}
