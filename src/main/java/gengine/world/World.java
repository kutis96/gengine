package gengine.world;

import gengine.util.coords.Coords3D;
import gengine.world.entity.WorldEntity;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface World {

    /**
     * Gets the World size.
     *
     * @return returns the size of the World.
     */
    public Coords3D getWorldSize();

    /**
     * Updates the World.
     *
     * @param dt time elapsed since last tick in millisecods.
     */
    public void tick(long dt);

    /**
     * Adds an WorldEntity to this World.
     * @param entity WorldEntity to add to this world.
     *
     */
    public void addEntity(WorldEntity entity);

    /**
     * Gets an array of all the entities in this World.
     *
     * @return an array of all world entities.
     */
    public WorldEntity[] getEntities();
}