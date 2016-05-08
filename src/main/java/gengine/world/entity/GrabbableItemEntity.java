package gengine.world.entity;

import gengine.logic.facade.WorldControllerFacade;
import gengine.world.entity.inventory.ItemStack;
import java.awt.Image;
import java.awt.Point;

/**
 * An entity containing an ItemStack one could potentially grab!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class GrabbableItemEntity extends TiledWorldEntity {

    private final ItemStack is;

    private final Image img;

    public GrabbableItemEntity(WorldControllerFacade facade, ItemStack is) {
        super(facade);
        this.is = is;
        this.img = is.getPrototype().render();
    }

    @Override
    public int getState() {
        if (is.isEmpty()) {
            return TiledWorldEntity.STATE_DEAD;
        }

        return TiledWorldEntity.STATE_UNDEFINED;
    }

    @Override
    public void resetState() {
        //unused
    }

    @Override
    public void tick(long dt) {
        //unused
    }

    @Override
    public boolean mouseHit(Point point) {
        return point.x > -img.getWidth(null) / 2 && point.x < img.getWidth(null)
                && point.y > -img.getHeight(null) / 2 && point.y < img.getHeight(null);
    }

    @Override
    public Image render() {
        return this.img;
    }

}
