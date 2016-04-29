package gengine.logic;

import gengine.events.EventGenerator;
import gengine.rendering.WorldRenderer;
import gengine.world.World;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Controller - contains and controls all world updating and rendering,
 * potentially also event generation.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Controller {
    
    //TODO: window resizing should redraw the world
    //TODO: add event handling
    
    private static final Logger LOG = Logger.getLogger(Controller.class.getName());
    
    private final Simulator sim;
    private final World world;
    private final JFrame jf;
    private final WorldRenderer wren;
    private final EventGenerator[] evgens;
    
    public Controller(Simulator sim, World world, JFrame jf, WorldRenderer wren, EventGenerator[] evgens) {
        this.sim = sim;
        this.world = world;
        this.jf = jf;
        this.wren = wren;
        this.evgens = evgens;
        
        init();
    }
    
    private Thread tSim;
    private Thread[] tEvgens;
    private Thread tRenderer;
    
    private RenderingWorker ivan;

    /**
     * Sets up the threads to run the Simulator and all the EventGenerators.
     */
    private void init() {
        this.tSim = new Thread(sim);

        //TODO: finish event generator stuff.
        /*
         this.tEvgens = new Thread[this.evgens.length];
         for (int i = 0; i < this.evgens.length; i++) {
         this.tEvgens[i] = new Thread();
         }
         */
        ivan = new RenderingWorker(jf, 20);
        ivan.setWorld(world);
        ivan.setWorldRenderer(wren);
        
        this.tRenderer = new Thread(ivan);
    }

    /**
     * Starts all the threads created during init.
     */
    public void start() {
        if (!this.tSim.isAlive()) {
            this.tSim.start();
        }
        
        if (!this.tRenderer.isAlive()) {
            this.tRenderer.start();
        }

        //TODO: finish event generator stuff.
        /*
         for (Thread teg : this.tEvgens) {
         if (!teg.isAlive()) {
         teg.start();
         }
         }
         */
    }
    
    public void stop() {
        
        this.sim.stop();
        this.ivan.stop();
        
        try {
            //TODO: finish event generator stuff.
            /*
             for(EventGenerator : this.evgens){
            
             }
             */
            
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pause() {
        
    }
}
