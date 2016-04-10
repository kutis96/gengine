package gengine.iwishjavahadtraits;

import gengine.events.Event;
import gengine.events.EventListener;

public interface HasListeners {

    /**
     * Attaches the given EventListener to an object.
     *
     * @param l An EventListener to attach.
     */
    public void attachListener(EventListener l);

    /**
     * Detaches the given EventListener to an object.
     *
     * @param l An EventListener to detach.
     */
    public void detachListener(EventListener l);

    /**
     * Detaches all controllers from an object.
     */
    public void clearListeners();

    /**
     * Dispatches an event for an object with all the controllers.
     *
     * @param e An event to dispatch.
     */
    public void dispatchEvent(Event e);
}
