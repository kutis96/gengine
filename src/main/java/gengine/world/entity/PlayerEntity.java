package gengine.world.entity;

import gengine.world.WorldFacade;
import java.awt.Image;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class PlayerEntity extends NPCEntity {

    public PlayerEntity(WorldFacade facade, int invsize) {
        super(facade, invsize);
    }
    
    //TODO: EVERYTHING
    
    @Override
    public abstract void tick(long dt);

    @Override
    public abstract Image render();
}