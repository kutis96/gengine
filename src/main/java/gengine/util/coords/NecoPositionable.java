package gengine.util.coords;

/**
 * A generic 'trait' enabling one to get or set positions of an object! How
 * brilliant.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface NecoPositionable {

    /**
     * Gets the object's coordinates.
     *
     * @return the object's coordinates
     */
    public Neco3D getPos();

    /**
     * Sets the object's position. Should return false on failure - i.e. when
     * some objects are meant to be static for some reason.
     *
     * @param pos The new coordinates
     */
    public void setPos(Neco3D pos);
}
