package gengine.world;

import gengine.util.facade.WorldFacade;
import gengine.util.facade.TiledWorldFacade;
import gengine.rendering.squaregrid.SquareGridUtils;
import gengine.util.coords.*;
import gengine.world.entity.WorldEntity;
import gengine.world.entity.TiledWorldEntity;
import gengine.world.tile.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A World made of Tiles!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World, TiledWorldFacade {

    private static final Logger LOG = Logger.getLogger(TiledWorld.class.getName());

    //TODO: use TileSets and Tile ID's instead - should save a fair bunch of memory
    private final int[][][] tileIDmap;
    private Tileset tileset;
    public final List<TiledWorldEntity> entities;

    private final Object entLock = new Object();

    /**
     * Maximum allowed amount of tiles. Mostly pointless.
     */
    public static final int MAXTILES = 1000000;

    /**
     * Constructs a TiledWorldWithTileSet of a specified size.
     *
     * @param size Size of the given world.
     *
     * @throws WorldSizeException Thrown when an invalid world size is supplied.
     */
    public TiledWorld(Coords3D size) throws WorldSizeException {

        if (size == null) {
            throw new WorldSizeException("Invalid world size: null supplied");
        }
        if (size.getX() < 1 || size.getY() < 1 || size.getZ() < 1) {
            throw new WorldSizeException("Invalid world size: " + size.toString());
        }
        if (size.getX() * size.getY() * size.getZ() > MAXTILES) {
            throw new WorldSizeException("Invalid world size: requested size too large");
        }

        this.tileIDmap = new int[(int) size.getX()][(int) size.getY()][(int) size.getZ()];

        this.entities = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns a tile with given coordinates.
     *
     * @param pos
     *
     * @return tile on the given position
     */
    public Tile getWorldtile(Coords3D pos) {

        pos.roundAll();

        if (SquareGridUtils.isWithin(pos, new Coords3D(), this.getWorldSizeMinusOne())) {
            try {
                return this.tileset.getTileFromId(
                        this.tileIDmap[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()]
                );
            } catch (NullPointerException | ArrayIndexOutOfBoundsException | TilesetIndexException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            //out of bounds
            return null;
        }
    }

    /**
     * Sets a given tile in this World to a specified Tile.
     *
     * @param tileID the ID of a tile to set
     * @param pos    the position to place the tile on
     */
    public void setWorldtile(int tileID, Coords3D pos) {
        this.tileIDmap[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()] = tileID;
    }

    /**
     * Sets a given tile in this World to a specified Tile.
     *
     * @param tile
     * @param pos  the position to place the tile on
     */
    public void setWorldtile(Tile tile, Coords3D pos) {

        int id = this.tileset.getTileID(tile);

        if (id == -1) {
            LOG.log(Level.FINE, "Adding new tile to the world's tileset through setWorldTile");
            id = this.tileset.addTile(tile);
        }

        this.setWorldtile(id, pos);
    }

    /**
     * Returns the size of this world. If it just so happens the world's tile
     * array is null, a null is returned.
     *
     * @return Coords3D containing the size of the world array. May return null
     *         when something goes horribly wrong.
     */
    @Override
    public Coords3D getWorldSize() {
        if (this.tileIDmap == null) {
            //TODO: change this to an exception
            return null;
        }

        return new Coords3D(
                this.tileIDmap.length, //X
                this.tileIDmap[0].length, //Y
                this.tileIDmap[0][0].length //Z
        );
    }

    /**
     * Returns the worldsize minus one on each dimension. Fairly useful for
     * iterating over the whole thing etc.
     *
     * @return Coords3D containing the size of the world array, minus (1,1,1)
     */
    private Coords3D getWorldSizeMinusOne() {
        try {
            return new Coords3D(getWorldSize().decrement(new Coords3D(1, 1, 1)));
        } catch (ValueException | DimMismatchException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return new Coords3D();
        }
    }

    public long getTileAmount() {
        long n = 1;
        
        for(float f : this.getWorldSize().toArray()){
            n *= (long)f;
        }
        
        return n;
    }

    /**
     * 'Ticks' all the entities and tiles.
     *
     * @param dt Delta Tee in milliseconds.
     */
    @Override
    public void tick(long dt) {
        this.tileset.updateAll(dt);

        synchronized (this.entLock) {
            TiledWorldEntity last = null;

            for (TiledWorldEntity e : this.entities) {
                if (e != null) {
                    last = e;
                    try {
                        e.tick(dt);
                    } catch (Exception ex) {
                        LOG.log(Level.SEVERE, "Last: " + last, ex);
                    }
                }
            }
        }
    }

    @Override
    public void addEntity(WorldEntity entity) {
        if (entity instanceof TiledWorldEntity) {
            synchronized (this.entLock) {
                this.entities.add((TiledWorldEntity) entity);
            }
        } else {
            LOG.log(Level.SEVERE, "Tried to add invalid entity type! {0} {1}", new Object[]{entity.toString(), entity.getClass().getName()});
        }
    }

    @Override
    public void removeEntity(WorldEntity entity) {
        if (entity instanceof TiledWorldEntity) {
            synchronized (this.entLock) {
                if (this.entities.contains((TiledWorldEntity) entity)) {
                    this.entities.remove((TiledWorldEntity) entity);
                } else {
                    LOG.log(Level.INFO, "Tried to remove a WorldEntity that wasn't actually present in the World. Strange.");
                }
            }
        } else {
            LOG.log(Level.SEVERE, "Tried to remove an invalid entity type! {0} {1}", new Object[]{entity.toString(), entity.getClass().getName()});
        }
    }

    @Override
    public WorldEntity[] getEntities() {
        synchronized (this.entLock) {
            return this.entities.toArray(new WorldEntity[this.entities.size()]);
        }
    }

    public Tileset getTileSet() {
        return this.tileset;
    }

    @Override
    public WorldFacade getFacade() {
        return (TiledWorldFacade) this;
    }

    public Tileset getTileset() {
        return tileset;
    }

    public void setTileset(Tileset tileset) {
        this.tileset = tileset;
    }

    @Override
    public Tile getTile(Coords3D coordinates) {
        //TODO: synchronize
        return this.getWorldtile(coordinates);
    }

    @Override
    public void setTile(Coords3D coordinates, Tile tile) {
        //TODO: synchronize
        this.setWorldtile(tile, coordinates);
    }

    @Override
    public boolean spawnEntity(TiledWorldEntity twe) {
        synchronized (this.entLock) {
            this.entities.add(twe);
        }
        return true;
    }
}
