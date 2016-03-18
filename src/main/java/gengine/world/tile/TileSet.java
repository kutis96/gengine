package gengine.world.tile;

import java.io.FileInputStream;

/**
 * 
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface TileSet {
    /**
     * Gets a tile with the specific ID.
     * @param id Tile ID
     * @return null if not found, correct Tile otherwise.
     */
    public Tile getTileFromId(int id);
    
    /**
     * Loads a TileSet from a file.
     * 
     * @param f
     * @return 
     */
    public boolean loadTileSet(FileInputStream f);
    
    /**
     * Updates all updatable (ie. animated) tiles in the tileset.
     */
    public void updateAll();
}
