package gengine.world.tile;

import gengine.world.tile.tiles.NullTile;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A set of tiles used within the TiledWorld. Stores all displayed tiles and
 * provides a nice way of updating all of them. This has been done mostly for
 * performance reasons.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public final class Tileset {

    private static final Logger LOG = Logger.getLogger(Tileset.class.getName());

    private final ArrayList<Tile> tileset;

    public Tileset() {
        this.tileset = new ArrayList<>();
        this.addTile(new NullTile());
    }

    /**
     * Gets a tile with the specific ID.
     *
     * @param id Tile ID
     *
     * @return corresponding Tile
     *
     * @throws TilesetIndexException when an invalid index is supplied
     */
    public Tile getTileFromId(int id) throws TilesetIndexException {
        if (id < 0 || id > this.tileset.size()) {
            LOG.log(Level.SEVERE, "Trying to get an invalid index {0}", id);
            throw new TilesetIndexException("Trying to get an invalid index " + id);
        } else {
            return tileset.get(id);
        }
    }

    /**
     * Returns an ID of the given tile.
     *
     * @param tile
     *
     * @return ID of the given file. Returns -1 if not found.
     */
    public int getTileID(Tile tile) {
        return this.tileset.indexOf(tile);
    }

    /**
     * Overwrites/adds a tile with a given ID.
     *
     * @param id   Tile ID to set
     * @param tile Tile to put at the given ID
     *
     * @throws TilesetIndexException thrown when an invalid ID is supplied.
     */
    public void setTileWithId(int id, Tile tile) throws TilesetIndexException {
        if (id < 0) {
            LOG.log(Level.SEVERE, "Trying to set an invalid index {0}", id);
            throw new TilesetIndexException("Trying to set an invalid index " + id);
        } else {
            try {
                if (tileset.get(id) != null) {
                    LOG.log(Level.FINE, "Overwriting a tile with index {0}", id);
                }
            } catch (Exception e) {
                //do nothing
            }

            tileset.set(id, tile);
        }
    }

    public int addTile(Tile tile) {
        this.tileset.add(tile);
        return this.tileset.lastIndexOf(tile);
    }

    /**
     * Loads a Tileset from a FileInputStream. I'm using a FileInputStream here,
     * so it's possible to load from resources within JAR files as well, etc.
     * Could be handy for single-file games etc.
     *
     * @param f FileInputStream to load a Tileset from.
     *
     * @return true on success, false on failure
     */
    public boolean loadTileSet(FileInputStream f) {
        //TODO: figure out the actual format >:/
        //TODO: do the actual loading
        //TODO: have fun
        return false;
    }

    /**
     * Updates all updatable (ie. animated) tiles in the tileset.
     *
     * @param dt delta-tee in milliseconds.
     */
    public void updateAll(long dt) {
        for (Tile tile : this.tileset) {
            tile.tick(dt);
        }
    }
}
