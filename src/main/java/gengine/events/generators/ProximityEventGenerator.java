package gengine.events.generators;

import gengine.events.receivers.ProximityEventReceiver;
import gengine.util.coords.Coords3D;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import java.util.logging.Logger;

/**
 * ProximityEventGenerator. Checks whether a given entity is in a close
 * proximity of another.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ProximityEventGenerator extends AbstWorldEventGenerator {

    private static final Logger LOG = Logger.getLogger(ProximityEventGenerator.class.getName());

    private final World world;
    
    public ProximityEventGenerator(World world, int period) {
        super(world, period);
        this.world = world;
    }

    @Override
    public void init() {
        //10 WORK
        //20 EAT
        //30 SLEEP
        //40 GOTO 10
        //RUN
        
        LOG.info("Initialized.");
    }

    @Override
    public void work(long dt) {
        //TODO: do the synchronization some other way, I still don't like this way very much.
        synchronized (this.world) {
            WorldEntity[] worldEntities = this.world.getEntities();

            for (WorldEntity we : worldEntities) {
                if (we instanceof ProximityEventReceiver) {
                    ProximityEventReceiver receiver = (ProximityEventReceiver) we;

                    float maxDistance = receiver.getProxDistance();
                    Coords3D pos = (Coords3D) we.getPos();

                    for (WorldEntity wx : worldEntities) {
                        if (!wx.equals(we)) {
                            float distance = getAbsDistance(pos, (Coords3D) wx.getPos());
                            if (distance >= maxDistance) {
                                //TODO: maybe add some dispatcher there,
                                // so the whole generator doesn't freeze just because of 
                                // some silly ugly event thing.
                                // Maybe not though. Let's just tell the people implementing
                                // those interfaces to be totally as efficient as they can be
                                // with their stuff, just because I couldn't be bothered.
                                // Sounds like a plan!

                                receiver.onProxEvent(wx, distance);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void die() {
        //BREAK
        
        LOG.info("Successfuly dead.");
    }

    /**
     * Returns the length of a bypass built between points A and B.
     *
     * Bypasses are devices which allow some people to dash from point A to
     * point B very fast whilst other people dash from point B to point A very
     * fast. People living at point C, being a point directly in between, are
     * often given to wonder what's so great about point A that so many people
     * of point B are so keen to get there, and what's so great about point B
     * that so many people of point A are so keen to get there. They often wish
     * that people would just once and for all work out where the hell they
     * wanted to be.
     *
     * Mr Prosser wanted to be at point D. Point D wasn't anywhere in
     * particular, it was just any convenient point a very long way from points
     * A, B and C. He would have a nice little cottage at point D, with axes
     * over the door, and spend a pleasant amount of time at point E, which
     * would be the nearest pub to point D. His wife of course wanted climbing
     * roses, but he wanted axes. He didn't know why - he just liked axes.
     *
     * The distace is VERY rarely 42.
     *
     * @param a Point A
     * @param b Point B
     *
     * @return Distance between point A and point B.
     *
     */
    private float getAbsDistance(Coords3D a, Coords3D b) {
        return a.distanceTo(b);
    }

}
