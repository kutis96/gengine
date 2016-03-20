package gengine.iwishjavahadtraits;

import gengine.util.coords.Coords;
import gengine.util.coords.DimMismatchException;

/**
 * A 'trait' used by anything with
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Positionable {
    
    /**
     * Gets the object's coordinates.
     *
     * @return the object's coordinates
     */
    public Coords getPos();
    
    /**
     * Sets the object's position. Should return false on failure - i.e. when
     * some objects are meant to be static for some reason.
     *
     * @param pos The new coordinates
     *
     * @throws DimMismatchException thrown on dimension mismatch
     */
    public void setPos(Coords pos) throws DimMismatchException;
}
