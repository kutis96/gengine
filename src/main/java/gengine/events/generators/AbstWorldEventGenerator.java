package gengine.events.generators;

import gengine.world.World;

/**
 * A generic event generator to generate events off of the World itself. This
 * class is _very_ abstract. It's mostly only created so I can use some
 * commonish constructor there.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class AbstWorldEventGenerator extends AbstEventGenerator {

    public AbstWorldEventGenerator(World w, int period) {
        super(period);
    }

    @Override
    public abstract void init();

    @Override
    public abstract void work(long dt);

    @Override
    public abstract void die();

}
