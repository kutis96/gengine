package gengine.world.entity;

import gengine.world.WorldFacade;
import gengine.world.entity.inventory.Inventory;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class NPCEntity extends WorldEntity{
    
    private Inventory inv;
    
    public NPCEntity(WorldFacade facade, int invsize){
        super(facade);
        this.inv = new Inventory(invsize);
    }
    
    public NPCEntity(WorldFacade facade){
        this(facade, 20);
    }
    
    public Inventory getInventory(){
        return this.inv;
    }
    
    @Override
    public void tick(long dt){
        
    }
    
}
