package gengine.world;

import gengine.util.coords.Coords3D;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.Tile;
import java.util.ArrayList;

/**
 * A World made of Tiles!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World {

    //TODO: use TileSets and Tile ID's instead - should save a fair bunch of memory
    
    private final Tile[][][] tiles;
    private ArrayList<WorldEntity> entities;

    /**
     * Maximum allowed amount of tiles. Mostly pointless.
     */
    public static final int MAXTILES = 1000000;

    /**
     * Constructs a TiledWorld of a specified size.
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

        this.tiles = new Tile[(int) size.getX()][(int) size.getY()][(int) size.getZ()];

        this.entities = new ArrayList<>();
    }

    /**
     * Returns a tile with given coordinates.
     *
     * @param pos
     *
     * @return tile on the given position
     */
    public Tile getWorldtile(Coords3D pos) {
        return this.tiles[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()];
    }

    /**
     * Sets a given tile in this World to a specified Tile.
     *
     * @param worldtile new tile to place at the given position
     * @param pos       the position to place the tile on
     */
    public void setWorldtile(Tile worldtile, Coords3D pos) {

        System.out.println("set\t" + pos.toString());

        this.tiles[(int) pos.getX()][(int) pos.getY()][(int) pos.getZ()] = worldtile;
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
        if (this.tiles == null) {
            return null;
        }

        return new Coords3D(
                this.tiles.length, //X
                this.tiles[0].length, //Y
                this.tiles[0][0].length //Z
        );
    }

    /**
     * 'Ticks' all the entities and tiles.
     *
     * @param dt Delta Tee in milliseconds.
     */
    @Override
    public void tick(long dt) {
        if (this.tiles != null) {
            for (int x = 0; x < this.tiles.length; x++) {
                for (int y = 0; y < this.tiles[x].length; y++) {
                    for (int z = 0; z < this.tiles[x][y].length; z++) {
                        Tile t = this.tiles[x][y][z];
                        if (t != null) {
                            t.tick(this, new Coords3D(x, y, z), dt);
                        }
                    }
                }
            }
        }

        for (WorldEntity e : this.entities) {
            if (e != null) {
                e.tick(this, dt);
            }
        }
    }

    @Override
    public void addEntity(WorldEntity entity) {
        this.entities.add(entity);
    }

    @Override
    public WorldEntity[] getEntities() {
        return this.entities.toArray(new WorldEntity[this.entities.size()]);
    }
}
