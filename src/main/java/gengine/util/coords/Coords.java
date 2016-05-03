package gengine.util.coords;

/**
 * An interface used to specify various world coordinates.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Coords {

    /**
     * Gets the dimension of the Coords.
     *
     * @return Coord dimension.
     */
    int getDimensions();

    /**
     * Sets the coordinates to something else.
     *
     * @param coords The new coordinates
     * @throws ValueException       Thrown when the given value is just plain
     *                              wrong in the way it contains NaNs and
     *                              infinities and nulls etc.
     */
    void setCoords(float[] coords) throws ValueException;

    /**
     * Returns the array of coordinates. May be null.
     *
     * @return
     */
    float[] getCoords();
}
