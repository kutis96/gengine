package gengine.events.generators;

import gengine.world.World;
import javax.swing.JPanel;

/**
 * A generic event generator with a constructor enabling one to get a JPanel
 * reference as well. Useful for grabbing various events off of the JPanel, as
 * expected. This class is _very_ abstract. It's mostly only created so I can
 * use some commonish constructor there.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class AbstWindowEventGenerator extends AbstEventGenerator {

    public AbstWindowEventGenerator(World w, JPanel f, int period) {
        super(period);
    }

    @Override
    public abstract void init();

    @Override
    public abstract void work(long dt);

    @Override
    public abstract void die();

}
