package gengine.world.entity;

import gengine.iwishjavahadtraits.Positionable;
import gengine.util.coords.Coords;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Entity extends Positionable {
    /**
     * Gets the Entity's position
     * @return coordinates of this entity
     */
    @Override
    Coords getCoords();
    
    /**
     * Sets the Entity's new position
     * 
     * @param pos
     * @return returns true on success, false on failure.
     */
    @Override
    boolean setCoords(Coords pos);
}
