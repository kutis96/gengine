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
    public void setCoords(int[] ic) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCoords(double[] dc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int[] getCoordsI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getCoordsD() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
