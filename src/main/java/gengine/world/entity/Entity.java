package gengine.world.entity;

import gengine.iwishjavahadtraits.Positionable;
import gengine.iwishjavahadtraits.Renderable;
import gengine.util.coords.Coords;
import gengine.world.World;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Entity extends Positionable, Renderable{
    /**
     * Gets the Entity's position
     * @return coordinates of this entity
     */ 
    @Override
    Coords getPos();
    
    /**
     * Sets the Entity's new position
     * 
     * @param pos
     * @return returns true on success, false on failure.
     */
    @Override
    boolean setPos(Coords pos);
    
    public void tick(World w, long dt);
}
