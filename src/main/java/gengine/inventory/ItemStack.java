package gengine.inventory;

import gengine.inventory.items.InventoryItem;

/**
 * An item stack. Useful for storing multiples of something.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ItemStack {

    private final InventoryItem prototype;
    private final int maxAmount;
    private int amount;

    /**
     * Constructs an ItemStack with the given prototype item.
     *
     * @param prototype The Item to stack.
     */
    public ItemStack(InventoryItem prototype) {
        this(prototype, prototype.getStackability());
    }

    private ItemStack(InventoryItem prototype, int maxAmount) {
        this.prototype = prototype;
        this.amount = 1;
        this.maxAmount = maxAmount;
    }

    /**
     * Gets an item from the ItemStack.
     *
     * @return a stacked item when the stack isn't empty, null otherwise.
     */
    public InventoryItem getItem() {
        if (this.amount > 0) {
            this.amount--;
            return prototype;
        } else {
            return null;
        }
    }

    /**
     * Adds an item to the ItemStack.
     *
     * @param item InventoryItem to add to the ItemStack.
     *
     * @return true when item equals the prototype and the stack isn't full (aka
     *         when it gets actually added), false otherwise.
     */
    public boolean addItem(InventoryItem item) {
        if (this.prototype.equals(item) && !this.isFull()) {
            this.amount++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the ItemStack is empty.
     *
     * @return true when the stack is empty.
     */
    public boolean isEmpty() {
        return this.amount <= 0;
    }

    /**
     * Checks whether the ItemStack is full.
     *
     * @return true when the stack is full.
     */
    public boolean isFull() {
        return this.amount >= this.maxAmount;
    }

    /**
     * Returns the amount of items stacked.
     *
     * @return amount of items on this stack.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Returns the prototype of this stack. Only to be used for drawing etc.
     *
     * @return returns the ItemStack's prototype. Care has to be taken not to
     *         overwrite it/modify it. Do use something else for that sort of purpose.
     */
    public InventoryItem getPrototype() {
        return this.prototype;
    }
}
