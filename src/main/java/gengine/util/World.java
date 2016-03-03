package gengine.util;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface World {

    /**
     * Polls the World size.
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
}
