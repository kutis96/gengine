package gengine._wip.test;

import gengine.util.Worker;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class GLoopyTest {

    private static final Logger LOG = Logger.getLogger(GLoopyTest.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Worker gl = new Worker(5) {

            @Override
            public void init() {
                LOG.log(Level.INFO, "INIT");
            }

            @Override
            public void work(long dt) {
                LOG.log(Level.INFO, "WORK {0}", dt);
            }

            @Override
            public void die() {
                LOG.log(Level.INFO, "DIE");
            }
        };
        
        gl.setPeriod(1000);
        
        Thread t = new Thread(gl);
        
        t.start();
        
        
        Thread.sleep(5000);
        
        gl.setPeriod(200);
        
        Thread.sleep(1000);
        
        LOG.log(Level.INFO, "STOPPING");
        
        gl.stop();
        
        while(!gl.isStopped()){
            LOG.log(Level.INFO, "waiting");
            Thread.sleep(10);
        }
        
        LOG.log(Level.INFO, "STOPPED");
        
        System.exit(0);
    }
}
