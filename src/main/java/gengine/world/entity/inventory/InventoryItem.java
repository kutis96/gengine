package gengine.world.entity.inventory;

/**
 * A generic inventory item.
 * 
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class InventoryItem {
    public int getStackability(){
        return 99;
    }
    
    public abstract int getType();
}
