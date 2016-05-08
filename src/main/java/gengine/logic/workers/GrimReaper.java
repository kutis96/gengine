package gengine.logic.workers;

import gengine.util.Worker;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GrimReaper. Basically just a dead WorldEntity garbage collector. I thought
 * this name would be much more fitting.
 *
 * I've decided to use this 'passive' method instead of WorldEntities basically
 * killing themselves, and that's mostly for safety reasons let's say.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class GrimReaper extends Worker {

    private static final Logger LOG = Logger.getLogger(GrimReaper.class.getName());

    private final World world;

    public GrimReaper(World world, int period) {
        super(period);
        this.world = world;
    }

    @Override
    public void init() {
        LOG.info("Alive. Unlike some of you.");
    }

    @Override
    public void work(long dt) {
        synchronized (this.world) {
            for (WorldEntity we : this.world.getEntities()) {
                if (we.getState() == WorldEntity.STATE_DEAD) {
                    LOG.log(Level.INFO, "And so I take another one... ({0})", we);
                    this.world.removeEntity(we);
                }
            }
        }
    }

    @Override
    public void die() {
        LOG.info("Dead.");
    }

}
