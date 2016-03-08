package gengine.world.entity;

import gengine.util.coords.Coords;
import gengine.util.coords.Coords3D;
import gengine.world.World;
import java.awt.Image;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class PlayerEntity implements Entity {

    @Override
    public Coords getPos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setPos(Coords pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick(World w, long dt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image render() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param direction
     * @return 
     */
    public boolean move(Coords3D direction){
        return false;
    }
    
}
