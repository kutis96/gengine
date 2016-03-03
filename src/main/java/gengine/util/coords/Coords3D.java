package gengine.util.coords;

/**
 * Coordinates to specify position in 3D worlds with integer coordinates.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords3D implements Coords {

    private float[] pos;

    public Coords3D(int x, int y, int z) {
        this.pos = new float[]{x,y,z};
    }
    
    public float getX() {
        return pos[0];
    }
    public float getY() {
        return pos[1];
    }
    public float getZ() {
        return pos[2];
    }

    @Override
    public int getDimensions() {
        return 3;
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
