package gengine.world;

import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
import gengine.world.tile.TilesetIndexException;

/**
 * A façade masking a TiledWorld's methods for guaranteed safe access by the
 * WorldEntities.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorldFacade implements WorldFacade {

    private final TiledWorld world;

    public TiledWorldFacade(TiledWorld world) {
        this.world = world;
    }

    /**
     * Returns a tile on a given coordinate.
     *
     * @param coordinates
     *
     * @return tile if the tile exists, null otherwise.
     */
    public Tile getTile(Coords3D coordinates) throws TilesetIndexException{
        synchronized(this.world){
            return this.world.getWorldtile(coordinates);
        }
    }

    /**
     * Sets a tile on a given coordinate.
     * @param coordinates
     * @param tile 
     */
    public void setTile(Coords3D coordinates, Tile tile){
        synchronized(this.world){
            this.world.setWorldtile(tile, coordinates);
        }
    }

    public Coords3D getWorldSize() {
        return this.world.getWorldSize();
    }
}
