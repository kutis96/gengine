package gengine.world;

import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;

/**
 * A World made of Tiles!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World {

    private final Coords3D size;

    private final Tile[][][] worldtiles;

    //TODO: add entity handling
    
    /**
     * Constructs a TiledWorld of a specified size.
     *
     * @param size Size of the given world.
     */
    public TiledWorld(Coords3D size) {
        //TODO: size validation

        this.size = size;
        this.worldtiles = new Tile[(int) size.getX()][(int) size.getY()][(int) size.getZ()];

        //this.entities = new LinkedList<>();
    }

    /**
     * Returns a tile with given coordinates.
     *
     * @param pos
     * @return tile on the given position
     */
    public Tile getWorldtile(Coords3D pos) {
        return this.worldtiles[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()];
    }
    
    /**
     * Sets a given tile in this World to a specified Tile.
     *
     * @param worldtile new tile to place at the given position
     * @param pos       the position to place the tile on
     */
    public void setWorldtile(Tile worldtile, Coords3D pos) {
        this.worldtiles[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()] = worldtile;
    }

    @Override
    public Coords3D getWorldSize() {
        return size;
    }

    @Override
    public void tick(long dt) {
        if (this.worldtiles != null) {
            for (Tile[][] ttt : this.worldtiles) {
                for (Tile[] tt : ttt) {
                    for (Tile t : tt) {
                        if (t != null) {
                            t.tick(this, dt);
                        }
                    }
                }
            }
        }
    }
}
