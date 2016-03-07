package gengine.world;

import gengine.util.coords.Coords3D;
import gengine.world.entity.Entity;
import gengine.world.tile.Tile;
import java.util.LinkedList;

/**
 * A World made of Tiles!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World {

    private final Tile[][][] tiles;
    private LinkedList<Entity> entities;

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
        
        this.entities = new LinkedList<>();
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

    @Override
    public Coords3D getWorldSize() {
        if(this.tiles == null){
            return new Coords3D(-1, -1, -1);
        }
        
        return new Coords3D(
                this.tiles.length,          //X
                this.tiles[0].length,       //Y
                this.tiles[0][0].length     //Z
        );
    }

    @Override
    public void tick(long dt) {
        if (this.tiles != null) {
            for (Tile[][] ttt : this.tiles) {
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

    @Override
    public int addEntity(Entity entity) {
        this.entities.add(entity);
        
        return -1;
    }

    @Override
    public Entity[] getEntities() {
        return this.entities.toArray(new Entity[this.entities.size()]);
    }

    @Override
    public Entity getEntity(int entity_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
