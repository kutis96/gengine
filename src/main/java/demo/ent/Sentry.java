package demo.ent;

import gengine.events.receivers.ProximityEventReceiver;
import gengine.logic.ControllerFacade;
import gengine.util.coords.*;
import gengine.util.facade.TiledWorldFacade;
import gengine.world.entity.*;
import gengine.world.entity.inventory.items.Weapon;
import gengine.world.entity.projectiles.Projectile;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Sentry extends NPCEntity implements ProximityEventReceiver {

    private static final Logger LOG = Logger.getLogger(Sentry.class.getName());

    private final Weapon weap;
    public static final int STATE_FIRING = 200;
    public static final int STATE_COOLDOWN = 201;
    public static final int STATE_DEFAULT = WorldEntity.STATE_UNDEFINED;

    private int state;
    private volatile WorldEntity enemy;
    private long lastFire;

    private Image img;

    private final ControllerFacade facade;

    public Sentry(ControllerFacade facade) throws IOException {
        super(facade);

        this.img = ImageIO.read(new File("/home/rkutina/testimages/redball.png"));

        this.facade = facade;
        this.weap = new Weapon() {

            @Override
            public int getMaxDamage() {
                return 10;
            }

            @Override
            public int getRateOfFireX100() {
                return 100;
            }
        };

        this.state = STATE_DEFAULT;
    }

    @Override
    public void tick(long dt) {

        switch (this.state) {
            case STATE_DEFAULT: {
                if (this.enemy != null && this.enemy.getPos().distanceTo(this.getPos()) <= this.getProxDistance()) {
                    this.state = STATE_COOLDOWN;
                    this.lastFire = 0;
                }
                break;
            }

            case STATE_COOLDOWN: {
                LOG.info("Cooling down...");
                if (this.lastFire * 100 > this.weap.getRateOfFireX100()) {
                    this.state = STATE_FIRING;
                } else {
                    this.lastFire += dt;
                }
                break;
            }

            case STATE_FIRING: {
                LOG.info("Firing!");

//                try {
//                    Projectile projProt;
//                    projProt = new SentryProjectile(facade, weap, null);
//                    projProt.setVelocity(new Coords3D(0.001, 0.001, 0));
//                    projProt.setPos(this.getPos());
//
//                    TiledWorldFacade twf = (TiledWorldFacade) this.facade.getWorldFacade();
//                    twf.spawnEntity(projProt);
//
//                } catch (ValueException ex) {
//                    LOG.log(Level.SEVERE, null, ex);
//                }

                this.state = STATE_COOLDOWN;
                this.lastFire = 0;
            }
        }
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
    public boolean mouseHit(Point point) {
        return false;
    }

    @Override
    public Image render() {
        return this.img;
    }

    @Override
    public void onProxEvent(WorldEntity closeEntity, float distance) {
        this.enemy = closeEntity;
    }

    @Override
    public float getProxDistance() {
        return 5;
    }

    @Override
    public void receiveAttack(Weapon w, NPCEntity perpetrator) {
        //do nothing, I'm indestructible >:D
    }

}
