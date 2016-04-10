package gengine.world.entity;

import gengine.world.entity.inventory.Inventory;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class NPCEntity extends WorldEntity{
    
    private Inventory inv;
    
    public NPCEntity(int invsize){
        this.inv = new Inventory(invsize);
    }
    
    public NPCEntity(){
        this(20);
    }
    
    public Inventory getInventory(){
        return this.inv;
    }
    
    public void tick(long dt){
        
    }
    
}
