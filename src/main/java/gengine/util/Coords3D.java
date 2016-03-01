package gengine.util;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords3D extends Coords2D{
    
    private final int z;
    
    public Coords3D(int x, int y, int z){
        super(x, y);
        this.z = z;
    }
    
    public int getZ(){
        return z;
    }
}
