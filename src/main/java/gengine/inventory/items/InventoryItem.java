package gengine.inventory.items;

import gengine.rendering.Renderable;

/**
 * A generic inventory item.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class InventoryItem implements Renderable {

    /**
     * A value specifying a maximum amount of this entity on the ItemStack.
     *
     * @return a max amount of this entity on an ItemStack, defaults to 99.
     */
    public int getStackability() {
        return 99;
    }

}
