package gengine.world.entity;

import gengine.logic.ControllerFacade;
import gengine.util.interfaces.Renderable;
import gengine.util.interfaces.Positionable;
import gengine.util.coords.*;
import gengine.util.facade.TiledWorldFacade;
import gengine.util.facade.WorldFacade;
import gengine.world.tile.TilesetIndexException;
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
public abstract class TiledWorldEntity extends WorldEntity implements Positionable, Renderable {

    private final ControllerFacade facade;

    public TiledWorldEntity(ControllerFacade facade) {
        super(facade);
        this.facade = facade;
    }

    private static final Logger LOG = Logger.getLogger(TiledWorldEntity.class.getName());

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
    @Override
    public abstract int getState();

    /**
     * Should reset the entity's state to something reasonable.
     */
    @Override
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
    @Override
    public void advancePos(Coords3D pos) {
        try {
            this.pos.increment(pos);
        } catch (DimMismatchException | ValueException ex) {
            LOG.log(Level.SEVERE, "", ex);
        }
    }

    public boolean advancePos(Coords3D pos, boolean checkForMissingTiles) {
        return this.advancePos(pos, checkForMissingTiles, true);
    }

    public boolean advancePos(Coords3D pos, boolean checkForMissingTiles, boolean checkForWalls) {

        synchronized (this.facade) {

            TiledWorldFacade twfacade = (TiledWorldFacade) this.facade.getWorldFacade();

            try {
                Coords3D npos = new Coords3D(this.pos.add(pos));

                if (null == twfacade.getTile(npos)) {
                    //the tile I'm trying to move on is a null, so screw this
                    if (checkForMissingTiles) {
                        return false;
                    }
                }

                if (checkForWalls && twfacade.getTile(npos).isWall()) {
                    LOG.info("IT IS A WALL!");
                    return false;
                }

                this.advancePos(pos);
            } catch (DimMismatchException | ValueException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return false;
            }
            return true;
            
        }
    }

    /**
     * Ticks (updates) the entity.
     *
     * @param dt Delta-tee in milliseconds (time since the last update).
     */
    @Override
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
    @Override
    public abstract boolean mouseHit(Point point);

}
