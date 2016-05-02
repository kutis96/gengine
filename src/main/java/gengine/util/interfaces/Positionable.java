package gengine.util.interfaces;

import gengine.util.coords.Coords3D;
import gengine.util.coords.DimMismatchException;

/**
 * A 'trait' used by anything with getPos and setPos. I have no idea why did I
 * make an interface for that, but ah well.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Positionable {

    /**
     * Gets the object's coordinates.
     *
     * @return the object's coordinates
     */
    public Coords3D getPos();

    /**
     * Sets the object's position. Should return false on failure - i.e. when
     * some objects are meant to be static for some reason.
     *
     * @param pos The new coordinates
     *
     * @throws DimMismatchException thrown on dimension mismatch
     */
    public void setPos(Coords3D pos) throws DimMismatchException;
}
