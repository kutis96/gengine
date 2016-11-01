package gengine.logic.facade;

import gengine.util.neco.Neco3D;
import gengine.world.tile.Tile;

/**
 * A façade masking a TiledWorld's methods for guaranteed safe access by the
 * WorldEntities.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface TiledWorldFacade extends WorldFacade {

    /**
     * Returns a tile on a given coordinate.
     *
     * @param coordinates
     *
     * @return tile if the tile exists, null otherwise.
     */
    public Tile getTile(Neco3D coordinates);

    /**
     * Sets a tile on a given coordinate.
     *
     * @param coordinates
     * @param tile
     */
    public void setTile(Neco3D coordinates, Tile tile);

    /**
     * Returns the size of the World.
     *
     * @return World size in an unadjusted Neco3D. To get the actual numbers,
     * access the internal array.
     */
    public int[] getWorldSize();
}
