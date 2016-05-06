package gengine.events.generators;

import gengine.events.receivers.VisibilityEventReceiver;
import gengine.util.coords.*;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * VisibilityEventGenerator. Checks whether a given entity is in a close
 * proximity of another.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class VisibilityEventGenerator extends AbstWorldEventGenerator {

    private static final Logger LOG = Logger.getLogger(VisibilityEventGenerator.class.getName());

    private final World world;

    public VisibilityEventGenerator(World world, int period) {
        super(world, period);
        this.world = world;
        throw new UnsupportedOperationException("This class is rather unfinished.");
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
        synchronized (this.world) {
            WorldEntity[] worldEntities = this.world.getEntities();

            for (WorldEntity we : worldEntities) {
                if (we instanceof VisibilityEventReceiver) {
                    VisibilityEventReceiver receiver = (VisibilityEventReceiver) we;

                    float maxDistance = receiver.getVisDistance();
                    Coords3D pos = we.getPos();

                    for (WorldEntity wx : worldEntities) {
                        if (!wx.equals(we)) {
                            float distance = pos.distanceTo(wx.getPos());

                            if (distance <= maxDistance
                                    && checkVisibility(this.world, pos, wx.getPos())) {
                                receiver.onVisEvent(wx, distance);
                            }   //c
                        }   //c
                    }   //c
                }   //c
            }   //combo
        }   //breaker
    }   //!!!

    @Override
    public void die() {
        //BREAK

        LOG.info("Successfuly dead.");
    }

    private boolean checkVisibility(World world, Coords3D pos1, Coords3D pos2) {
        if (world instanceof TiledWorld) {
            /*try {*/
                TiledWorld tw = (TiledWorld) world;

                //do something
                //ACTUALLY CHECK THE VISIBILITY HERE
                
                return false;

            /*} catch (DimMismatchException | ValueException ex) {
                Logger.getLogger(VisibilityEventGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }*/

        } else {
            throw new UnsupportedOperationException("TiledWorld is the only supported world type at this point.");
        }

        //return false;
    }

}
