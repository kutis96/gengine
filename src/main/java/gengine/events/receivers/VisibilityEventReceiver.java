package gengine.events.receivers;

import gengine.world.entity.WorldEntity;

/**
 * A receiver to get events from the VisibilityEventGenerator.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface VisibilityEventReceiver {

    /**
     * This method will be called once any WorldEntity is within the visible
     * range specified by the getVisDistance call.
     *
     * @param closeEntity An entity that seems to be close.
     * @param distance    Distance of the entity that's close
     */
    public void onVisEvent(WorldEntity closeEntity, float distance);

    /**
     * Called by the event generator to figure out what on earth is meant to be
     * close enough, and what definitely isn't.
     *
     * @return sensing radius
     */
    public float getVisDistance();
}
