package gengine.tile;

import gengine.util.TiledWorldEntity;
import gengine.util.Coords3D;
import gengine.util.World;
import java.util.LinkedList;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorld implements World {

    private final Coords3D size;
    
    private final Tile[][][] worldtiles;
    private LinkedList<TiledWorldEntity> entities;
    
    //TODO: add entity handling

    public TiledWorld(Coords3D size){
        this.size = size;
        this.worldtiles = new Tile[size.getX()][size.getY()][size.getZ()];
        this.entities = new LinkedList<>();
    }
    
    public Tile getWorldtile(Coords3D pos) {
        return this.worldtiles[pos.getX()][pos.getY()][pos.getZ()];
    }
    
    public Tile[][][] getWorldtiles() {
        return this.worldtiles;
    }
    
    public void setWorldtile(Tile worldtile, Coords3D pos) {
        this.worldtiles[pos.getX()][pos.getY()][pos.getZ()] = worldtile;
    }
    
    public Coords3D getWorldSize(){
        return size;
    }

    @Override
    public Coords3D getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
