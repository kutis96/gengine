package gengine.world;

import gengine.logic.facade.WorldFacade;
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
    public int[] getWorldSize();

    /**
     * Updates the World.
     *
     * @param dt time elapsed since last tick in millisecods.
     */
    public void tick(long dt);

    /**
     * Adds a WorldEntity to this World.
     *
     * @param entity WorldEntity to add to this world.
     *
     * @return true on success, false on failure.
     */
    public boolean addEntity(WorldEntity entity);

    /**
     * Removes a WorldEntity from this World.
     *
     * @param entity WorldEntity to remove from this world.
     */
    public void removeEntity(WorldEntity entity);

    /**
     * Gets an array of all the entities in this World.
     *
     * @return an array of all world entities.
     */
    public WorldEntity[] getEntities();

    /**
     * Gets a new WorldFacade for this world.
     *
     * @return new WorldFacade referencing to this world.
     */
    public WorldFacade getFacade();
}
