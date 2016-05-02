package gengine.util.coords;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Coordinates to specify position in 2D worlds.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords2D extends CoordsFixedD {

    /**
     * Constructs the Coords2D with default value of (0, 0).
     */
    public Coords2D() {
        super(2);

        try {
            this.setCoords(new float[]{0, 0});
        } catch (DimMismatchException ex) {
            //This should actually never happen
            Logger.getLogger(Coords3D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ValueException ex) {
            //This should actually never happen
            Logger.getLogger(Coords2D.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructs the Coords2D with a given default value
     *
     * @param x default x-coordinate
     * @param y default y-coordinate
     *
     * @throws ValueException
     */
    public Coords2D(float x, float y) throws ValueException {
        super(2);

        try {
            this.setCoords(new float[]{x, y});
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
}
