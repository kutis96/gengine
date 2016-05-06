package gengine.logic;

import gengine.events.generators.AbstEventGenerator;
import gengine.rendering.*;
import gengine.util.coords.Coords3D;
import gengine.util.facade.WorldFacade;
import gengine.world.World;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Controller - contains and controls all world updating and rendering,
 * potentially also event generation.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Controller implements ControllerFacade, WindowListener {

    //TODO: window resizing should redraw the world
    //TODO: add event handling
    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    private final Simulator sim;
    private final JFrame jf;
    private final WorldRenderer wren;
    private final AbstEventGenerator[] evgens;

    private Thread tSim;
    private Thread[] tEvgens;
    private Thread tRenderer;

    private RenderingWorker renWor;

    private final Stack<World> worldStack;

    public Controller(Simulator sim, World world, JFrame jf, WorldRenderer wren, AbstEventGenerator[] evgens) {
        this.sim = sim;
        this.jf = jf;
        this.wren = wren;
        this.evgens = evgens;

        this.worldStack = new Stack<>();
        this.worldStack.push(world);

        init();
    }

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
        renWor.setWorld(this.getCurrentWorld());
        renWor.setWorldRenderer(wren);
        WorldRendererOptions wrop = new WorldRendererOptions();
        wrop.setZoom(1);
        wrop.addFlag(WorldRendererOptions.Flags.DEBUGMODE);
        renWor.setRendererOptions(wrop);

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

        LOG.info("Started");
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

    private World getCurrentWorld() {
        return this.worldStack.peek();
    }

    @Override
    public void changeCamPosition(Coords3D pos) {

        WorldRendererOptions wrop = this.renWor.getRendererOptions();
        wrop.setCameraPosition(pos);
        this.renWor.setRendererOptions(wrop);

    }

    @Override
    public WorldFacade getWorldFacade() {
        return this.getCurrentWorld().getFacade();
    }

    @Override
    public boolean switchWorlds(Object requestor, World world, boolean save) throws WorldTypeMismatchException {
        //TODO: check whether the requestor is actually allowed to do this
        //TODO: actually save the player itself and their stuff before any world switching occurs
        //      and set them back into the new/old one again. this could be slightly tricky

        if (world.getClass().equals(this.getCurrentWorld().getClass())) { //check whether both worlds are of the same type
            if (!save) {
                this.worldStack.pop();
            }
            this.worldStack.push(world);
        }

        return true;
    }

    @Override
    public boolean returnWorlds(Object requestor) {
        //TODO: check whether the requestor is actually allowed to do this

        if (this.worldStack.size() > 1) {
            this.worldStack.pop();
            return true;
        }
        return false;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //Do nothing
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //Do nothing
        //maybe pause the thing
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //Do nothing
        //maybe unpause the thing
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //maybe unpause the thing
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //maybe pause the thing
    }
}
