package gengine.world;

import gengine.util.coords.Coords3D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface World {

    /**
     * Polls the World size.
     *
     * @deprecated This function will be removed by the time of the next commit.
     * @return returns the size of the World.
     */
    public Coords3D getWorldSize();

    /**
     * Updates the World.
     *
     * @param dt time elapsed since last tick in millisecods.
     */
    public void tick(long dt);
}
