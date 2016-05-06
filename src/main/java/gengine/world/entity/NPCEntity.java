package gengine.world.entity;

import gengine.logic.ControllerFacade;
import gengine.world.entity.inventory.Inventory;
import gengine.world.entity.inventory.items.Weapon;
import java.awt.Point;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class NPCEntity extends TiledWorldEntity {

    private Inventory inv;

    public NPCEntity(ControllerFacade facade, int invsize) {
        super(facade);
        this.inv = new Inventory(invsize);
    }

    public NPCEntity(ControllerFacade facade) {
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
