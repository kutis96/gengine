package gengine.util.coords;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Coordinates to specify position in 3D worlds.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Coords3D extends CoordsFixedD {

    private static final Logger LOG = Logger.getLogger(Coords3D.class.getName());

    /**
     * Constructs the Coords2D with a default value of (0, 0, 0).
     */
    public Coords3D() {
        super(3);

        try {
            this.setCoords(new float[]{0, 0, 0});
        } catch (ValueException ex) {
            //This should actually never happen
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Clones the given Coords3D values into a new Coord3D object.
     *
     * @param thingtoclone Coords3D to clone.
     *
     * @throws ValueException thrown in the rare case of the cloned object
     *                        containing invalid values.
     */
    public Coords3D(Coords3D thingtoclone) throws ValueException {
        super(3);
        this.setCoords(thingtoclone.getCoords());
    }

    public Coords3D(CoordsFixedD thingtoclone) throws ValueException {
        super(3);
        this.setCoords(thingtoclone.getCoords());
    }

    /**
     * Constructs the Coords2D with a given default value
     *
     * @param x default x-coordinate
     * @param y default y-coordinate
     * @param z default z-coordinate
     *
     * @throws ValueException
     */
    public Coords3D(float x, float y, float z) throws ValueException {
        super(3);
        this.setCoords(new float[]{x, y, z});
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
