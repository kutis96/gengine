package gengine.world.entity;

import gengine.util.interfaces.Renderable;
import gengine.util.interfaces.Positionable;
import gengine.util.coords.*;
import gengine.world.World;

/**
 * WorldEntity is meant to be a generic container for any moving or otherwise
 * interesting parts of a World. Basically, anything in a World that isn't a
 * Tile should be WorldEntity. And Tiles are meant to be the static things!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldEntity implements Positionable, Renderable {

    private Coords3D pos;

    /**
     * Gets the WorldEntity's position
     *
     * @return coordinates of this entity
     */
    @Override
    public Coords getPos() {
        return this.pos;
    }

    /**
     * Returns the current WorldEntity's state it happens to be in. Example
     * states: EN_ROUTE, IDLE, ATTACKING, etc.
     *
     * @return
     */
    public abstract int getState();

    /**
     * Should reset the entity's state to something reasonable.
     */
    public abstract void resetState();

    /**
     * Sets the WorldEntity's new position.
     *
     * @param pos new position
     *
     * @throws DimMismatchException thrown when the dimensions don't mach the
     *                              required dimensions (3).
     */
    @Override
    public void setPos(Coords pos) throws DimMismatchException {
        if (pos == null || pos.getDimensions() != 3) {
            throw new DimMismatchException();
        }
        this.pos = (Coords3D) pos;
    }

    /**
     * Advance the WorldEntity's position by a given offset. This could be handy
     * for animations or incremental stuff without a need to get and set the
     * position at the same time elsewhere in the code to do the same thing.
     *
     *
     * @param pos
     *
     * @throws DimMismatchException
     */
    public void advancePos(Coords pos) throws DimMismatchException {
        if (pos == null || pos.getDimensions() != 3) {
            throw new DimMismatchException();
        }
        this.pos.add((CoordsFixedD) pos);
    }

    /**
     * Ticks (updates) the entity.
     *
     * @param w  The world this entity resides in.
     * @param dt Delta-tee in milliseconds (time since the last update).
     */
    public abstract void tick(World w, long dt);
    
}