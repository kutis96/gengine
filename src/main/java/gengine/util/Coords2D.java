package gengine.util;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords2D {
    
    private final int x, y;
    
    public Coords2D(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
