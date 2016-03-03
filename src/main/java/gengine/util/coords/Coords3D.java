package gengine.util.coords;

/**
 * Coordinates to specify position in 3D worlds with integer coordinates.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords3D implements Coords {

    private int[] pos;

    public Coords3D(int x, int y, int z) {
        this.pos = new int[]{x,y,z};
    }
    
    public int getX() {
        return pos[0];
    }
    
    public int getY() {
        return pos[1];
    }
    
    public int getZ() {
        return pos[2];
    }

    @Override
    public int getDimensions() {
        return 3;
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
