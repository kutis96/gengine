package gengine.ctrls;

import gengine.rendering.RendererException;
import gengine.rendering.WorldTypeMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ControllerUpdater implements Runnable {

    private static final Logger LOG = Logger.getLogger(ControllerUpdater.class.getName());

    private boolean running;
    private boolean stopped;

    private Exception exx;

    private final Controller c;
    private final int tickperiod;

    public ControllerUpdater(Controller c, int tickperiod) {
        this.running = true;
        this.stopped = false;
        this.exx = null;
        this.c = c;
        this.tickperiod = tickperiod;
    }

    @Override
    public void run() {

        long lastmillis = System.currentTimeMillis();
        long lastUPSmsg = lastmillis;
        long updcntr = 0;
        double avgdt = -1;
        
        while (running) {
            try {

                long dt;

                if ((dt = System.currentTimeMillis() - lastmillis) >= tickperiod)
                {
                    c.tick(dt);
                    c.render();
                    lastmillis = System.currentTimeMillis();
                    updcntr++;
                    
                    if(avgdt == -1){
                        avgdt = dt;
                    }
                    
                    avgdt += avgdt;
                    avgdt = avgdt/2;
                }
                
                if (System.currentTimeMillis() - lastUPSmsg >= 1000)
                {
                    System.out.println("UPS: " + updcntr + "\nAvg dt: " + avgdt);
                    updcntr = 0;
                    lastUPSmsg = System.currentTimeMillis();
                }
                
            } catch (WorldTypeMismatchException | RendererException ex) {
                LOG.log(Level.SEVERE, null, ex);
                this.running = false;
                this.exx = ex;
            }
        }
        this.stopped = true;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public boolean isRunning() {
        return this.running;
    }

    public Exception getException() {
        return this.exx;
    }

}
