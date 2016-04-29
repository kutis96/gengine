package gengine.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A generic runnable that loops about! I'm using this one basically everywhere;
 * it's quite convenient mostly because of the fact it has a settable period,
 * it's easy to start and stop, and all the stuff I rather like. I quickly
 * figured out I'm basically implementing all the same stuff all over and over
 * again; so that's why I made this thing so I don't repeat myself too much. In
 * case you wonder why on Earth would I call it like this, well. I find this
 * name somewhat descriptive.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class LoopyRunnable implements Runnable {

    private static final Logger LOG = Logger.getLogger(LoopyRunnable.class.getName());

    private boolean isRunning = false;
    private boolean isStopped = false;
    private int period;

    /**
     * Constructs this Runnable with a set repeating period.
     *
     * @param period
     */
    public LoopyRunnable(int period) {
        this.period = period;
    }

    /**
     * Brings this thread to a halt. Wait until isStop() is true to safely
     * dispose of this thread.
     */
    public void stop() {
        this.isRunning = false;
    }

    /**
     * Checks whether this LoopyRunnable is still set to be looping around.
     *
     * @return True when true, false when false. False returned here does not
     *         necessarily mean this LoopyRunnable is completed executing and
     *         ready to be disposed of!
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Checks whether this LoopyRunnable is done with its execution.
     *
     * @return True when true, false when false. True returned here means this
     *         LoopyRunnable is safe to be disposed of.
     */
    public boolean isStopped() {
        return this.isStopped;
    }

    /**
     * A setter for the runnable's loopy period.
     *
     * @param ms
     */
    public void setPeriod(int ms) {
        this.period = ms;
    }

    /**
     * A getter for the runnable's loopy period.
     *
     * @return repetition period of this loopy thing.
     */
    public int getPeriod() {
        return this.period;
    }

    /**
     * Runnable's run method. Do not modify.
     */
    @Override
    public void run() {
        this.isRunning = true;
        this.isStopped = false;

        init();

        long lastupdate = System.currentTimeMillis();

        while (this.isRunning) {

            try {

                long dt = System.currentTimeMillis() - lastupdate;
                lastupdate = System.currentTimeMillis();

                work(dt);

                Thread.sleep(Math.max(period - (System.currentTimeMillis() - lastupdate), 0), 1);

            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
                this.stop();
            }
        }

        die();

        this.isStopped = true;
    }

    /**
     * A method where this LoopyRunnable initializes all its variables and
     * stuff. Possibly. This method shall NEVER be called by something other
     * than the LoopyRunnable itself. The only reason this method is made public
     * is to make a simple abstract class a possibility.
     */
    public abstract void init();

    /**
     * A method where this LoopyRunnable does its actual work. This method shall
     * NEVER be called by something other than this class itself. The only
     * reason this method is made public is to make a simple abstract class a
     * possibility.
     *
     * @param dt Delta-tee in milliseconds.
     */
    public abstract void work(long dt);

    /**
     * A method in which everything regarding this LoopyRunnable should be
     * brought to a halt. This method shall NEVER be called by something other
     * than the LoopyRunnable itself. The only reason this method is made public
     * is to make a simple abstract class a possibility.
     */
    public abstract void die();
}
