package gengine.world.entity.inventory;

public abstract class InventoryItem {
    public int getStackability(){
        return 99;
    }
    
    public abstract int getType();
}
