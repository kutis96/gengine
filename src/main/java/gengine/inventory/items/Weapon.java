package gengine.inventory.items;

/**
 * An abstract weapon class to be extended by any actual weapons.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Weapon extends InventoryItem {

    /**
     * Returns maximum allowable damage by this weapon. This of course may
     * depend on the attacked thing's shield level and whatnot.
     *
     * @return maximum allowable damage by this weapon.
     */
    public abstract int getMaxDamage();

    /**
     * Returns a weapon's top rate of attack, times 100 to avoid floating point
     * numbers in there. Don't ask me why, it's just a nice round number.
     *
     * @return Rate of fire (per 100 seconds)
     */
    public abstract int getRateOfFireX100();

    @Override
    public int getStackability() {
        return 1;
    }

}
