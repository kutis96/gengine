package gengine.logic;

import gengine.events.generators.AbstEventGenerator;
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
    private final AbstEventGenerator[] evgens;

    public Controller(Simulator sim, World world, JFrame jf, WorldRenderer wren, AbstEventGenerator[] evgens) {
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

    private RenderingWorker renWor;

    /**
     * Sets up the threads to run the Simulator and all the EventGenerators.
     */
    private void init() {
        this.tSim = new Thread(sim);

        this.tEvgens = new Thread[this.evgens.length];
        for (int i = 0; i < this.evgens.length; i++) {
            this.tEvgens[i] = new Thread(this.evgens[i]);
        }

        renWor = new RenderingWorker(jf, 20);
        renWor.setWorld(world);
        renWor.setWorldRenderer(wren);

        this.tRenderer = new Thread(renWor);
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

        for (Thread teg : this.tEvgens) {
            if (!teg.isAlive()) {
                teg.start();
            }
        }
    }

    public void stop() {

        this.sim.stop();
        this.renWor.stop();

        for (AbstEventGenerator eg : evgens) {
            eg.stop();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pause() {
        //TODO: add pause stuffs!
        //should be basically the same as stop, no?
        this.stop();
    }
}
