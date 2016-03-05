package gengine.util.coords;

/**
 * Coordinates to specify position in 3D worlds with integer coordinates.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords3D extends CoordFixedD {

    private float[] pos;

    public Coords3D() {
        super(3);
    }
    
    public Coords3D(float x, float y, float z) {
        super(3);
        this.pos = new float[]{x, y, z};
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
}
