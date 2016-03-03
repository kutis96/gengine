package gengine.iwishjavahadtraits;

import gengine.util.coords.Coords;

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
    public Coords getCoords();
    
    /**
     * Sets the object's position. Should return false on failure - i.e. when
     * some objects are meant to be static for some reason.
     *
     * @param pos The new coordinates
     *
     * @return true on success, false on failure.
     */
    public boolean setCoords(Coords pos);
}
