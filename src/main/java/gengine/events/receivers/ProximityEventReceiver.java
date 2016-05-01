package gengine.events.receivers;

import gengine.world.entity.WorldEntity;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface ProximityEventReceiver {

    /**
     * This method will be called once any WorldEntity is within the proximity
     * radius specified by the getProxDistance call.
     *
     * @param closeEntity An entity that seems to be close.
     * @param distance Distance of the entity that's close
     */
    public void onProxEvent(WorldEntity closeEntity, float distance);

    /**
     * Called by the event generator to figure out what on earth is meant to be
     * close enough, and what definitely isn't.
     *
     * @return sensing radius
     */
    public float getProxDistance();
}
