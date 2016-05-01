package gengine.world.entity;

import gengine.util.interfaces.Renderable;
import gengine.util.interfaces.Positionable;
import gengine.util.coords.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WorldEntity is meant to be a generic container for any moving or otherwise
 * interesting parts of a World. Basically, anything in a World that isn't a
 * Tile should be WorldEntity. And Tiles are meant to be the static things!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldEntity implements Positionable, Renderable {

    private static final Logger LOG = Logger.getLogger(WorldEntity.class.getName());
    
    private Coords3D pos;

    /**
     * Gets the WorldEntity's position
     *
     * @return coordinates of this entity
     */
    @Override
    public Coords3D getPos() {
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
     */
    @Override
    public void setPos(Coords3D pos) {
        this.pos = pos;
    }

    /**
     * Advance the WorldEntity's position by a given offset. This could be handy
     * for animations or incremental stuff without a need to get and set the
     * position at the same time elsewhere in the code to do the same thing.
     *
     *
     * @param pos
     */
    public void advancePos(Coords3D pos) {
        try {
            this.pos.increment(pos);
        } catch (DimMismatchException ex) {
            LOG.warning("Dim Mismatch found here. The fok.");
            LOG.warning(ex.getMessage());
        } catch (ValueException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ticks (updates) the entity.
     *
     * @param dt Delta-tee in milliseconds (time since the last update).
     */
    public abstract void tick(long dt);
    
}