package gengine.util.coords;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Coordinates to specify position in 3D worlds.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords3D extends CoordFixedD {

    /**
     * Constructs the Coords2D with a default value of (0, 0, 0).
     */
    public Coords3D() {
        super(3);
        
        try {
            this.setCoords(new float[]{0, 0, 0});
        } catch (DimMismatchException ex) {
            //This should actually never happen
            Logger.getLogger(Coords3D.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructs the Coords2D with a given default value
     *
     * @param x default x-coordinate
     * @param y default y-coordinate
     * @param z default z-coordinate
     */
    public Coords3D(float x, float y, float z) {
        super(3);
        
        try {
            this.setCoords(new float[]{x, y, z});
        } catch (DimMismatchException ex) {
            //This should actually never happen
            Logger.getLogger(Coords3D.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the X-coordinate
     *
     * @return the X-coordinate
     */
    public float getX() {
        return this.getCoords()[0];
    }

    /**
     * Gets the Y-coordinate
     *
     * @return the Y-coordinate
     */
    public float getY() {
        return this.getCoords()[1];
    }

    /**
     * Gets the Z-coordinate
     *
     * @return the Z-coordinate
     */
    public float getZ() {
        return this.getCoords()[2];
    }
}
