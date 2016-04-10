package gengine.events;

import gengine.iwishjavahadtraits.HasListeners;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class EventGenerator implements HasListeners, Runnable {

    private final ArrayList<EventListener> listeners = new ArrayList<>();

    private boolean isRunning = false;
    private boolean isStopped = false;
    private int sleeptime = 5;

    public void stop() {
        this.isRunning = false;
    }

    /**
     * Checks whether this EventGenerator is still set to be looping around.
     *
     * @return True when true, false when false. False returned here does not
     *         necessarily mean this EventGenerator is completed executing and
     *         ready to be disposed of!
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Checks whether this EventGenerator is done with its execution.
     *
     * @return True when true, false when false. True returned here means this
     *         EventGenerator is safe to be disposed of.
     */
    public boolean isStopped() {
        return this.isStopped;
    }

    /**
     * A setter for the sleep delay within this generator's run loop.
     *
     * @param ms
     */
    public void setSleeptime(int ms) {
        this.sleeptime = ms;
    }

    /**
     * Attaches an EventListener to this generator.
     *
     * @param l EventListener to attach
     */
    @Override
    public void attachListener(EventListener l) {
        this.listeners.add(l);
    }

    /**
     * Detaches all EventListeners from this generator.
     */
    @Override
    public void clearListeners() {
        this.listeners.clear();
    }

    /**
     * Detaches a given EventListener from this generator.
     *
     * @param l EventListener to detach.
     */
    @Override
    public void detachListener(EventListener l) {
        this.listeners.remove(l);
    }

    /**
     * Try to dispatch a given Event through this EventGenerator. Use at your
     * own risk.
     *
     * @param e Event to dispatch to all Generator's Listeners.
     */
    @Override
    public abstract void dispatchEvent(Event e);

    /**
     * Runnable's run method. Do not modify.
     */
    @Override
    public void run() {
        this.isRunning = true;
        this.isStopped = false;

        init();
        
        while (this.isRunning) {

            try {
                work();

                Thread.sleep(sleeptime);

            } catch (InterruptedException ex) {
                Logger.getLogger(EventGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        die();

        this.isStopped = true;
    }

    /**
     * A method where this EventGenerator initializes all its variables and
     * stuff. Possibly. This method shall NEVER be called by something other
     * than the EventGenerator itself. The only reason this method is made
     * public is to make a simple abstract class a possibility.
     */
    public abstract void init();

    /**
     * A method where this EventGenerator does its actual work. This method
     * shall NEVER be called by something other than the EventGenerator itself.
     * The only reason this method is made public is to make a simple abstract
     * class a possibility.
     */
    public abstract void work();

    /**
     * A method in which everything regarding this EventGenerator should be
     * brought to a halt. This method shall NEVER be called by something other
     * than the EventGenerator itself. The only reason this method is made
     * public is to make a simple abstract class a possibility.
     */
    public abstract void die();

}
