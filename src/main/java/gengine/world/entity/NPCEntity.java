package gengine.world.entity;

import gengine.logic.facade.WorldControllerFacade;
import gengine.inventory.Inventory;
import gengine.inventory.ItemStack;
import gengine.inventory.items.Weapon;
import java.awt.Point;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class NPCEntity extends TiledWorldEntity {

    private Inventory inv;

    private final WorldControllerFacade facade;

    public NPCEntity(WorldControllerFacade facade, int invsize) {
        super(facade);
        this.facade = facade;
        this.inv = new Inventory(invsize);
    }

    public NPCEntity(WorldControllerFacade facade) {
        this(facade, 20);
    }

    public Inventory getInventory() {
        return this.inv;
    }

    @Override
    public abstract void tick(long dt);

    public abstract void receiveAttack(Weapon w, NPCEntity perpetrator);

    @Override
    public abstract int getState();

    @Override
    public abstract void resetState();

    @Override
    public abstract boolean mouseHit(Point point);

}
