package gengine.world;

import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A World made of Tiles!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World {
    
    private static final Logger LOG = Logger.getLogger(TiledWorld.class.getName());

    //TODO: use TileSets and Tile ID's instead - should save a fair bunch of memory
    private final int[][][] tileIDmap;
    private final Tileset tileset;
    private final List<WorldEntity> entities;

    /**
     * Maximum allowed amount of tiles. Mostly pointless.
     */
    public static final int MAXTILES = 1000000;

    /**
     * Constructs a TiledWorldWithTileSet of a specified size.
     *
     * @param size    Size of the given world.
     * @param tileset Tileset to use.
     *
     * @throws WorldSizeException Thrown when an invalid world size is supplied.
     */
    public TiledWorld(Coords3D size, Tileset tileset) throws WorldSizeException {

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

        this.tileset = tileset;

        this.entities = new ArrayList<>();
    }

    /**
     * Returns a tile with given coordinates.
     *
     * @param pos
     *
     * @return tile on the given position
     * @throws TilesetIndexException This exception can be thrown when
     */
    public Tile getWorldtile(Coords3D pos) throws TilesetIndexException {
        return this.tileset.getTileFromId(
                this.tileIDmap[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()]
        );
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
     * @param pos    the position to place the tile on
     */
    public void setWorldtile(Tile tile, Coords3D pos) {

        int id = this.tileset.getTileID(tile);
        
        if(id == -1){
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
        try {
            if (this.tileIDmap == null) {
                return null;
            }
            
            return new Coords3D(
                    this.tileIDmap.length, //X
                    this.tileIDmap[0].length, //Y
                    this.tileIDmap[0][0].length //Z
            );
        } catch (ValueException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 'Ticks' all the entities and tiles.
     *
     * @param dt Delta Tee in milliseconds.
     */
    @Override
    public void tick(long dt) {
        this.tileset.updateAll(dt);

        for (WorldEntity e : this.entities) {
            if (e != null) {
                e.tick(dt);
            }
        }
    }

    @Override
    public void addEntity(WorldEntity entity) {
        synchronized(this.entities){
            this.entities.add(entity);
        }
    }

    @Override
    public WorldEntity[] getEntities() {
        synchronized(this.entities){
            return this.entities.toArray(new WorldEntity[this.entities.size()]);
        }
    }
    
    public Tileset getTileSet(){
        return this.tileset;
    }
}
