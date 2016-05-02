package gengine.world.tile;

import gengine.util.interfaces.Renderable;

/**
 * Tile, used in TileWorlds to create the static environment out of.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Tile implements Renderable {

    private boolean isWall;

    /**
     * Checks whether this tile is a wall or not. To be used with various
     * pathfinding algorithms.
     *
     * @return true when it is a wall, false if not.
     */
    public boolean isWall() {
        return this.isWall;
    }

    /**
     * Sets the wall-ness of this tile.
     *
     * @param iswall true if this tile is meant to act like a wall, false if
     *               not.
     */
    public void setWall(boolean iswall) {
        this.isWall();
    }

    /**
     * Tick, used for various animations
     *
     * @param dt Delta-tee in milliseconds
     */
    public abstract void tick(long dt);
}
