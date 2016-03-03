package gengine.tile;

import gengine.ent.WorldEntity;
import gengine.util.*;
import gengine.util.coords.Coords;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Tile extends Visible implements Renderable, WorldEntity {
    private final Coords pos;
    
    public Tile(Coords pos){
        this.pos = pos;
    }
    
    public void tick(TiledWorld iw, long dt){
        //TODO: add logger here
    }

    @Override
    public Coords getCoords() {
        return this.pos;
    }
    
    @Override
    public boolean setCoords(Coords pos) {
        return false;   //tiles are meant to stay in one place after all.
    }
}
