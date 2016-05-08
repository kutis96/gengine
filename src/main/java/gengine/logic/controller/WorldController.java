package gengine.logic.controller;

import gengine.logic.facade.WorldControllerFacade;
import gengine.logic.workers.Simulator;
import gengine.events.generators.AbstEventGenerator;
import gengine.logic.callback.NullCallback;
import gengine.rendering.*;
import gengine.util.coords.Coords3D;
import gengine.logic.facade.WorldFacade;
import gengine.logic.view.WorldView;
import gengine.world.World;
import java.awt.BorderLayout;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * WorldController - contains and controls all world updating and rendering,
 * potentially also event generation.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WorldController implements WorldControllerFacade {

    //TODO: window resizing should redraw the world

    private static final Logger LOG = Logger.getLogger(WorldController.class.getName());

    private final Simulator sim;
    private final JPanel jp;
    private final AbstEventGenerator[] evgens;

    private Thread tSim;
    private Thread[] tEvgens;

    private final WorldView wv;

    private final Stack<World> worldStack;

    public WorldController(Simulator sim, World world, JPanel jp, WorldRenderer wren, AbstEventGenerator[] evgens) {
        this.sim = sim;
        this.jp = jp;
        this.jp.setVisible(true);

        this.wv = new WorldView(world, wren);
        this.wv.setVisible(true);

        this.jp.setLayout(new BorderLayout());
        this.jp.add(wv, BorderLayout.CENTER);
        this.jp.doLayout();

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

    }

    /**
     * Starts all the threads created during init.
     */
    public void start() {

        if (!this.tSim.isAlive()) {
            this.tSim.start();
        }

        this.wv.initialize(new NullCallback());

        for (Thread teg : this.tEvgens) {
            if (!teg.isAlive()) {
                teg.start();
            }
        }

        LOG.info("Started");
    }

    public void stop() {

        this.sim.stop();

        this.wv.deconstruct(new NullCallback());    //TODO: make use of these callbacks

        for (AbstEventGenerator eg : evgens) {
            eg.stop();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldController.class.getName()).log(Level.SEVERE, null, ex);
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

        this.wv.changeCamPosition(pos);

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
}
