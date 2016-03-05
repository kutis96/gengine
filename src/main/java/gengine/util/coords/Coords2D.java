package gengine.util.coords;

/**
 * Coordinates to specify position in 2D worlds with integer coordinates.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords2D extends CoordFixedD {

    private float[] pos;

    public Coords2D() {
        super(2);
    }

    public Coords2D(float x, float y) {
        super(2);
        this.pos = new float[]{x, y};
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
