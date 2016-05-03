package gengine.util.coords;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Coordinates to specify position in 2D worlds.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords2D extends CoordsFixedD {

    private static final Logger LOG = Logger.getLogger(Coords2D.class.getName());

    /**
     * Constructs the Coords2D with default value of (0, 0).
     */
    public Coords2D() {
        super(2);
        try {
            this.setCoords(new float[]{0, 0});
        } catch (ValueException ex) {
            //This should actually never happen
            LOG.log(Level.SEVERE, null, ex);
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
        this.setCoords(new float[]{x, y});
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
