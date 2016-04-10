package gengine.iwishjavahadtraits;

import gengine.ctrls.Controller;
import gengine.ctrls.events.Event;

public interface Controllable {

    /**
     * Attaches the given Controller to an object.
     *
     * @param c A controller to attach.
     */
    public void attachController(Controller c);

    /**
     * Detaches all controllers from an object.
     */
    public void clearControllers();

    /**
     * Dispatches an event for an object with all the controllers.
     *
     * @param e An event to dispatch.
     */
    public void dispatchEvent(Event e);
}
