package gengine.world.entity.projectiles;

import gengine.events.receivers.ProximityEventReceiver;
import gengine.logic.facade.WorldControllerFacade;
import gengine.util.coords.Neco3D;
import gengine.world.entity.*;
import gengine.inventory.items.Weapon;
import java.awt.Image;
import java.awt.Point;
import java.util.logging.Logger;

/**
 * An abstract Projectile class. To be extended by any actual projectiles used
 * in the games.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Projectile extends TiledWorldEntity implements ProximityEventReceiver {

    private static final Logger LOG = Logger.getLogger(Projectile.class.getName());

    public static final int STATE_FLYING = 101;
    public static final int STATE_CRASHED = 102;

    private Neco3D velocity;
    private final Weapon weapon;
    private final NPCEntity perpetrator;

    private int state;

    /**
     *
     * @param facade Facade to interface the world with
     * @param weapon Weapon this Projectile was shot from
     * @param perpetrator NPCEntity shooting said Weapon
     */
    public Projectile(WorldControllerFacade facade, Weapon weapon, NPCEntity perpetrator) {
        super(facade);
        this.state = STATE_FLYING;
        this.weapon = weapon;
        this.perpetrator = perpetrator;
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public void resetState() {
        //do nothing
    }

    @Override
    public void tick(long dt) {
        if (this.state == STATE_FLYING && this.advancePos((Neco3D) new Neco3D(velocity).multiplyD(dt), true, true)) {
            //flight!
            //do some flying around
            this.fly(dt);
        } else {
            //crash!
            //crash and stuff
            this.state = STATE_CRASHED;
            this.crash(dt);
        }
    }

    @Override
    public boolean mouseHit(Point point) {
        return false; //who'd click projectiles? >:C
    }

    /**
     * To be implemented by something else.
     *
     * @return Image to be drawn.
     */
    @Override
    public abstract Image render();

    /**
     * To be modified once I get hitboxes done
     *
     * @return
     */
    @Override
    public float getProxDistance() {
        return (float) 0.5;
    }

    @Override
    public void onProxEvent(WorldEntity closeEntity, float distance) {
        if (closeEntity instanceof NPCEntity) {
            NPCEntity ent = (NPCEntity) closeEntity;
            ent.receiveAttack(weapon, perpetrator);
        }
    }

    /**
     * Do something when flying about. Like wiggle about or just fly in a
     * straight line.
     *
     * @param dt
     */
    public abstract void fly(long dt);

    /**
     * Do something when crashing. Like animate an explosion or something silly.
     *
     * @param dt
     */
    public abstract void crash(long dt);

    /**
     * Sets projectile velocity
     *
     * @param velocity
     */
    public void setVelocity(Neco3D velocity) {
        this.velocity = velocity;
    }
}
