package gengine.world.entity.inventory.items;

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

    /**
     * A value specifying the item's level. Totally your regular RPG-style.
     *
     * @return item's level, defaults to 1.
     */
    public int getLevel() {
        return 1;
    }
}
