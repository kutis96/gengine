package gengine.world.entity;

import gengine.logic.facade.WorldControllerFacade;
import gengine.rendering.Renderable;
import gengine.logic.facade.TiledWorldFacade;
import gengine.util.coords.Neco3D;
import gengine.inventory.ItemStack;
import gengine.world.tile.Tile;
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
public abstract class TiledWorldEntity extends WorldEntity implements Renderable {

    private final WorldControllerFacade facade;

    public TiledWorldEntity(WorldControllerFacade facade) {
        super(facade);
        this.facade = facade;
        this.pos = new Neco3D();
    }

    private static final Logger LOG = Logger.getLogger(TiledWorldEntity.class.getName());

    /**
     * Gets the WorldEntity's position
     *
     * @return coordinates of this entity
     */
    @Override
    public Neco3D getPos() {
        return super.getPos();
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
    public void setPos(Neco3D pos) {
        super.setPos(pos);
    }

    /**
     * Advance the WorldEntity's position by a given offset. This could be handy
     * for animations or incremental stuff without a need to get and set the
     * position at the same time elsewhere in the code to do the same thing.
     *
     * @param pos
     */
    @Override
    public void advancePos(Neco3D pos) {
        super.advancePos(pos);
    }

    public boolean advancePos(Neco3D pos, boolean checkForMissingTiles) {
        return this.advancePos(pos, checkForMissingTiles, true);
    }

    public boolean advancePos(Neco3D del, boolean checkForMissingTiles, boolean checkForWalls) {

        synchronized (this.facade) {

            TiledWorldFacade twfacade = (TiledWorldFacade) this.facade.getWorldFacade();

            Neco3D npos = this.pos.add(del);

            Tile t = twfacade.getTile(npos);

            if (checkForMissingTiles && null == t) {
                //the tile I'm trying to move on is a null, so screw this
                LOG.log(Level.FINER, "IT IS A NULL! {0}", npos);
                return false;
            } else if (checkForWalls && t.isWall()) {
                LOG.finer("IT IS A WALL!");
                return false;
            }

            this.advancePos(del);

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
     * case of the SquareGridRenderer, but there isn't much else implemented at
     * this point.)The IsometricWorldRenderer references its images to the
     * BOTTOM CENTER of the image. [citation needed]
     *
     * @return true when a given point actually mouseHit the thing, false when
     * not.
     */
    @Override
    public abstract boolean mouseHit(Point point);

    /**
     * Try to drop an ItemStack. Generates a new GrabbableItemEntity and spawns
     * it.
     *
     * @param droppedItem an ItemStack to drop
     * @param dropper WorldEntity supposed to drop this item
     * @param facade WorldControllerFacade to spawn the new GrabbableItemEntity
     * with
     *
     * @return true on success, false on failure.
     */
    public static boolean dropItemStack(ItemStack droppedItem, WorldEntity dropper, WorldControllerFacade facade) {
        //W: this could end up being a bit fishy
        //TODO: replace 'this' with parameters and make this thing static

        GrabbableItemEntity gie = new GrabbableItemEntity(dropper, facade, droppedItem);
        gie.setPos(dropper.getPos());

        return facade.spawnEntity(gie);
    }

}
