package gengine.events.generators;

import gengine.util.LoopyRunnable;

/**
 * AbstEventGenerator. A class so abstract it basically doesn't exist anywhere else
 * but in your own imagination.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class AbstEventGenerator extends LoopyRunnable {
    //TODO: figure out some basic stuff for this.
    //  the main issue is that the AbstEventGenerator itself could be generating the events out of basically anything
    //  I think I may create a few specific subsets of this one - say a WorldEventGenerator and a WindowEventGenerator
    //  one of them would definitely need to see a world, the other one would definitely want to see the window.
    //  I still don't think those two have all that much in common

    /**
     * Constructor inherited from the LoopyRunnable
     *
     * @param period Desired loop period in milliseconds
     */
    public AbstEventGenerator(int period) {
        super(period);
    }
}
