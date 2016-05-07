package gengine.util.facade;

import gengine.util.coords.Coords3D;
import gengine.world.entity.TiledWorldEntity;
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
    public Tile getTile(Coords3D coordinates);

    /**
     * Sets a tile on a given coordinate.
     *
     * @param coordinates
     * @param tile
     */
    public void setTile(Coords3D coordinates, Tile tile);

    /**
     * Returns the size of the World.
     *
     * @return World size
     */
    public Coords3D getWorldSize();

    /**
     * Adds an entity into the world.
     *
     * @param twe TiledWorldEntity to spawn.
     *
     * @return true on success, false never.
     */
    public boolean spawnEntity(TiledWorldEntity twe);
}
