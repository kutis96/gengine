package gengine.world.entity;

import gengine.logic.facade.WorldControllerFacade;
import gengine.rendering.Renderable;
import gengine.util.coords.Positionable;
import gengine.util.coords.*;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WorldEntity is meant to be a generic container for any moving or otherwise
 * interesting parts of a World. Basically, anything in a World that isn't a
 * Tile should be WorldEntity. And Tiles are meant to be the static things!
 *
 * Issue: I somehow need to get the World reference there, to access the tiles
 * of the actual world. I'll probably use some façade there, to make the access
 * actually safe at all times.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldEntity implements Positionable, Renderable {

    private static final Logger LOG = Logger.getLogger(WorldEntity.class.getName());

    public static final int STATE_DEAD = 0xDEAD;
    public static final int STATE_UNDEFINED = -1;
    Coords3D pos;


    /**
     * Constructs this WorldEntity supplying it a WorldFacade it may use to get
     * some information off of the World it resides in.
     *
     * Or, it could get some other World's facade. An application of that could
     * quite possibly result in something interesting.
     *
     * @param facade
     */
    public WorldEntity(WorldControllerFacade facade) {
        //facade is to be used by extending classes
    }

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
     * STATE_DEAD would get this entity removed off of the world, by the means
     * of the GrimReaper's work.
     *
     * @return current WorldEntity state.
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
     * @param pos
     */
    public void advancePos(Coords3D pos) {
        try {
            this.pos.increment(pos);
        } catch (DimMismatchException | ValueException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ticks (updates) the entity.
     *
     * @param dt Delta-tee in milliseconds (time since the last update).
     */
    public abstract void tick(long dt);

    /**
     * Checks whether a specified point mouseHit this object. Used for mouse
     * stuff detection. Doing it like this allows various WorldEntities to have
     * various means of clickbox detection, which may be quite specific to each
     * of them... Or, it could make some unclickable at all.
     *
     * This method should only be used for clickbound checking, not for taking
     * any actions (unless you're doing something insanely cool and you know you
     * need to use this for reals.)
     *
     * @param point A point, referenced to the CENTER of the rendered image (in
     *              case of the SquareGridRenderer, but there isn't much else
     *              implemented at this point.)The IsometricWorldRenderer
     *              references its images to the BOTTOM CENTER of the image.
     *              [citation needed]
     *
     * @return true when a given point actually mouseHit the thing, false when
     *         not.
     */
    public abstract boolean mouseHit(Point point);

}
