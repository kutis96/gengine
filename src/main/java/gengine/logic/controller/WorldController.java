package gengine.logic.controller;

import gengine.events.generators.*;
import gengine.logic.facade.WorldControllerFacade;
import gengine.logic.workers.Simulator;
import gengine.logic.callback.NullCallback;
import gengine.rendering.*;
import gengine.util.coords.Coords3D;
import gengine.logic.facade.WorldFacade;
import gengine.logic.view.WorldView;
import gengine.logic.workers.GrimReaper;
import gengine.util.Worker;
import gengine.world.World;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * WorldController - contains and controls all world updating and rendering,
 * potentially also event generation.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WorldController implements WorldControllerFacade {

    //TODO: window resizing should redraw the world
    private static final Logger LOG = Logger.getLogger(WorldController.class.getName());

    private final AbstEventGenerator[] evgens;

    private final CopyOnWriteArrayList<Worker> workers;
    private final CopyOnWriteArrayList<Thread> threads;

    private final WorldView worldview;

    private final Stack<World> worldStack;

    public WorldController(World world, WorldView worldview) throws WorldTypeMismatchException {

        AbstEventGenerator[] newEvgens = {
            new ProximityEventGenerator(world, 25),
            new KeyboardEventGenerator(world, worldview, 50),
            new MouseEventGenerator(world, worldview, worldview.getRenderer(), 50)
        };
        

        this.worldview = worldview;
        this.worldview.setVisible(true);
        this.worldview.initialize(new NullCallback());
        
        this.worldview.setFocusable(true);
        this.worldview.requestFocus();

        this.evgens = newEvgens;

        this.worldStack = new Stack<>();
        this.worldStack.push(world);
        this.worldview.setWorld(world);

        this.workers = new CopyOnWriteArrayList<>();
        this.threads = new CopyOnWriteArrayList<>();

        init();
    }

    /**
     * Sets up the threads to run the Simulator and all the EventGenerators.
     */
    private void init() {
        this.workers.add(new Simulator(this.worldStack.peek(), 20));
        this.workers.add(new GrimReaper(this.worldStack.peek(), 60));

        this.workers.addAll(Arrays.asList(evgens));

        LOG.info("Initialized");
    }

    /**
     * Starts all the threads created during init.
     */
    public void start() {

        for (Worker w : this.workers) {
            Thread t = new Thread(w);
            this.threads.add(t);
            t.start();
        }

        LOG.info("Started");
    }

    /**
     * Stops all the threads. Hopefully.
     */
    public void stop() {

        for (Worker w : this.workers) {
            w.stop();
        }

        //TODO: add checks
    }

    public void pause() {
        //TODO: add pause stuffs!
        this.stop();
    }

    private World getCurrentWorld() {
        return this.worldStack.peek();
    }

    @Override
    public void changeCamPosition(Coords3D pos) {

        this.worldview.changeCamPosition(pos);

    }

    @Override
    public WorldFacade getWorldFacade() {
        return this.getCurrentWorld().getFacade();
    }

    @Override
    public boolean switchWorlds(Object requestor, World world, boolean save) throws WorldTypeMismatchException {
        //TODO: 

        if (!save && !this.worldStack.isEmpty()) {
            this.worldStack.pop();
        }

        this.worldStack.push(world);
        this.worldview.setWorld(world);

        return true;
    }

    @Override
    public boolean returnWorlds(Object requestor) {
        //TODO: 
        throw new UnsupportedOperationException();
    }
}
