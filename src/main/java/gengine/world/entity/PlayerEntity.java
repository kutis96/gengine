package gengine.world.entity;

import gengine.util.coords.Coords3D;
import gengine.world.World;
import java.awt.Image;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class PlayerEntity extends NPCEntity {
    
    public abstract void move(Coords3D pos);
    
    @Override
    public abstract void tick(World w, long dt);

    @Override
    public abstract Image render();
}