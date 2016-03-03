package gengine.util.coords;

/**
 * Coordinates to specify position in 2D worlds with integer coordinates.
 * 
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords2D implements Coords {
    
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

    @Override
    public int getDimensions() {
        return 2;
    }
    
    @Override
    public void setCoords(float[] coords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] getCoords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
