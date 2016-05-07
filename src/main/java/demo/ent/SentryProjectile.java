package demo.ent;

import gengine.logic.ControllerFacade;
import gengine.world.entity.NPCEntity;
import gengine.world.entity.inventory.items.Weapon;
import gengine.world.entity.projectiles.Projectile;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SentryProjectile extends Projectile {

    public SentryProjectile(ControllerFacade facade, Weapon weapon, NPCEntity perpetrator) {
        super(facade, weapon, perpetrator);
    }


    @Override
    public Image render() {
        BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = bi.getGraphics();
        g.setColor(Color.red);
        g.fillOval(0, 0, 10, 10);
        return bi;
    }

    @Override
    public void fly(long dt) {
        //do regular stuff
    }

    @Override
    public void crash(long dt) {
        //do regular stuff
    }
    
}
