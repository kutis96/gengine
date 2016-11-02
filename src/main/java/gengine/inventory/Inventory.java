package gengine.inventory;

import gengine.inventory.items.InventoryItem;
import java.util.ArrayList;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Inventory {

    private final ArrayList<ItemStack> inv;
    private final int size;

    /**
     * Constructs a new Inventory for storing InventoryItems.
     *
     * @param size
     */
    public Inventory(int size) {
        this.inv = new ArrayList<>(size);
        this.size = size;
    }
    
    /**
     *
     * @param item
     *
     * @return true on success, false on failure - ie. when the inventory is already full
     */
    public boolean addItem(InventoryItem item) {
        //Check for any ItemStacks able to take this item
        for(int i = 0; i < this.inv.size(); i++){
            ItemStack is = this.inv.get(i);
            
            if(is == null){
                //Skip the 'empty' ones for now
                continue;
            }
            
            if(is.addItem(item)){
                //Item was successfully added, we're done here.
                return true;
            }
        }
        
        for(int i = 0; i < this.inv.size(); i++){
            ItemStack is = this.inv.get(i);
            
            if(is == null){
                is = new ItemStack(item);
                this.inv.set(i, is);
                return true;
            }
        }
        
        return false;
    }
    
    public ItemStack[] getAllItems(){
        ItemStack[] ret = new ItemStack[this.size];
        
        for(int i = 0; i < this.inv.size(); i++){
            ItemStack is = this.inv.get(i);
            
            //remove the empty stacks here
            if(is.isEmpty()){
                this.inv.set(i, null);
            }
            
            ret[i] = is;
        }
        
        return ret;
    }
}
