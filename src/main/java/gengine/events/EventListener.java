package gengine.events;

/**
 * A generic EventListener interface. EventListener is meant to dispatch Events
 * generated by EventGenerators etc.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface EventListener {

    /**
     * Dispatch a given event. That is, perform some action upon the given
     * Event.
     *
     * @param e
     */
    public void dispatchEvent(Event e);
}