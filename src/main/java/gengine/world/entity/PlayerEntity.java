package gengine.world.entity;

import gengine.logic.facade.WorldControllerFacade;
import java.awt.Image;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class PlayerEntity extends NPCEntity {

    public PlayerEntity(WorldControllerFacade facade, int invsize) {
        super(facade, invsize);
    }
    
    //TODO: EVERYTHING
    
    @Override
    public abstract void tick(long dt);

    @Override
    public abstract Image render();
}