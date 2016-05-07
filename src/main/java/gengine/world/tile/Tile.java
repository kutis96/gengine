package gengine.world.tile;

import gengine.util.coords.Coords3D;
import gengine.rendering.Renderable;

/**
 * Tile, used in TileWorlds to create the static environment out of.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Tile implements Renderable {

    private boolean wallness = false;

    /**
     * Tick, used for various animations
     *
     * @param dt Delta-tee in milliseconds
     */
    public abstract void tick(long dt);

    /**
     * A function to be used by various visibility detecting algorithms.
     *
     * @param direction Direction vector, should be unit size
     * @param offset    Offset, counted from the center of the tile. The
     *                  magnitude of each coordinate is always from the interval
     *                  {-1, 1}.
     *
     * @return True when one can see through the given tile in the specified
     *         direction and offset, false if not.
     */
    public abstract boolean canSeeThrough(Coords3D direction, Coords3D offset);

    /**
     * A function to be used by various pathfinding algorithms to detect whether
     * a tile is a wall or not.
     *
     * @return True when a tile is indeed a wall, false if not.
     */
    public boolean isWall() {
        return this.wallness;
    }

    /**
     * Turns a tile into a wall or back.
     *
     * @param wallness
     */
    public void setWall(boolean wallness) {
        this.wallness = wallness;
    }
}
