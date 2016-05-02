package gengine.logic;

import gengine.util.LoopyRunnable;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import java.util.logging.Logger;

/**
 * GrimReaper. Basically just a dead WorldEntity garbage collector. I thought
 * this name is much more fitting.
 *
 * I've decided to use this 'passive' method instead of WorldEntities basically
 * killing themselves, and that's mostly for safety reasons let's say.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class GrimReaper extends LoopyRunnable {

    private static final Logger LOG = Logger.getLogger(GrimReaper.class.getName());

    private final World world;

    public GrimReaper(World world, int period) {
        super(period);
        this.world = world;
    }

    @Override
    public void init() {
        LOG.info("Good morning, Mr. Reaper. Why do you look so grim today? D:");
    }

    @Override
    public void work(long dt) {
        synchronized (this.world) {
            for (WorldEntity we : this.world.getEntities()) {
                if (we.getState() == WorldEntity.STATE_DEAD) {
                    LOG.info("And so the Death takes another one...");
                    this.world.removeEntity(we);
                }
            }
        }
    }

    @Override
    public void die() {
        LOG.info("And so the Death decides its work is over.");
    }

}
