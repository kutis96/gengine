package gengine.ent;

import gengine.util.coords.Coords;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Entity {
    /**
     * Gets the Entity's position
     * @return coordinates of this entity
     */
    Coords getCoords();
    
    /**
     * Sets the Entity's new position
     * 
     * @param pos
     * @return returns true on success, false on failure.
     */
    boolean setCoords(Coords pos);
}
