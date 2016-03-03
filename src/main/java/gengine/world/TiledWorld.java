package gengine.tile;

import gengine.util.coords.Coords3D;
import gengine.util.World;

/**
 * A World made of Tiles!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World {

    private final Coords3D size;

    //TODO: switch to something more efficient, most of the world would probably end up being empty anyway.
    private final Tile[][][] worldtiles;

    //TODO: add entity handling
    //private LinkedList<TiledWorldEntity> entities;
    /**
     * Constructs a TiledWorld of a specified size.
     *
     * @param size Size of the given world.
     */
    public TiledWorld(Coords3D size) {
        //TODO: size validation

        this.size = size;
        this.worldtiles = new Tile[size.getX()][size.getY()][size.getZ()];

        //this.entities = new LinkedList<>();
    }

    /**
     * Returns a tile with given coordinates.
     *
     * @param pos
     *
     * @return
     */
    public Tile getWorldtile(Coords3D pos) {
        return this.worldtiles[pos.getX()][pos.getY()][pos.getZ()];
    }

    /**
     * Returns an array of all world tiles. Should not be used like ever. This
     * function is about to be deprecated.
     *
     * @return a three-dimensional array of tiles.
     */
    public Tile[][][] getWorldtiles() {
        return this.worldtiles;
    }

    /**
     * Sets a given tile in this World to a specified Tile.
     *
     * @param worldtile new tile to place at the given position
     * @param pos       the position to place the tile on
     */
    public void setWorldtile(Tile worldtile, Coords3D pos) {
        this.worldtiles[pos.getX()][pos.getY()][pos.getZ()] = worldtile;
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
                            {
                                {
                                    {
                                        {
                                            {
                                                {
                                                    {
                                                        {
                                                            //TODO: ADD EVEN MORE CURLY BRACKETS AND NESTED LOOPS!
                                                            t.tick(this, dt);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
