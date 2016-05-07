package gengine.logic;

import gengine.events.generators.AbstWorldEventGenerator;
import gengine.world.World;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic Simulator. Simulators are meant to 'clock' the world's stacic stuff,
 * such as animations and statically moving entities.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Simulator extends AbstWorldEventGenerator {

    private static final Logger LOG = Logger.getLogger(Simulator.class.getName());

    private final World world;

    public Simulator(World world, int period) {
        super(world, period);
        this.world = world;
    }

    @Override
    public void init() {
        LOG.info("Simulator initialized.");
    }

    @Override
    public void work(long dt) {
        try {
            this.world.tick(dt);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Caught an exception while ticking the world!", ex);
        }
    }

    @Override
    public void die() {
        LOG.info("Simulator out.");
    }
}
