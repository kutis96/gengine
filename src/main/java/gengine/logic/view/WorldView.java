package gengine.logic.view;

import gengine.logic.callback.Callback;
import gengine.logic.workers.RenderingWorker;
import gengine.logic.exceptions.ItJustKeepsGoingException;
import gengine.rendering.WorldRenderer;
import gengine.rendering.WorldRendererOptions;
import gengine.util.neco.Neco3D;
import gengine.world.World;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WorldView. A View displaying the current game World.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class WorldView extends View {

    private static final Logger LOG = Logger.getLogger(WorldView.class.getName());

    private final Object lock;

    private RenderingWorker renwor;

    private Thread workerThread;

    public WorldView(World world, WorldRenderer wren) {
        this(wren);
        this.renwor.setWorld(world);
    }

    public WorldView(WorldRenderer wren) {
        this.lock = new Object();

        //TODO: parametrize this
        int targetFPS = 60;
        this.renwor = new RenderingWorker(this, 1000 / targetFPS);

        this.renwor.setWorldRenderer(wren);
    }

    @Override
    public void initialize(Callback callback) {

        this.workerThread = new Thread(this.renwor);
        this.workerThread.start();

        if (callback != null) {
            callback.callback();
        }
    }

    @Override
    public void deconstruct(Callback callback) {

        this.renwor.stop();

        int loopcounter = 0;
        while (!this.renwor.isStopped()) {
            try {
                Thread.sleep(10);
                if (loopcounter++ > 20) {
                    callback.callbackOnError(new ItJustKeepsGoingException("The worker ain't stopping!"));
                }
            } catch (InterruptedException ex) {
                LOG.log(Level.WARNING, null, ex);
            }
        }

        if (callback != null) {
            callback.callback();
        }
    }

    public void changeCamPosition(Neco3D pos) {

        WorldRendererOptions wrop = this.renwor.getRendererOptions();
        wrop.setCameraPosition(pos);
        this.renwor.setRendererOptions(wrop);

    }

    public void setWorld(World world) {
        //TODO: check for correct world type
        synchronized (lock) {
            this.renwor.setWorld(world);
        }
    }

    public WorldRenderer getRenderer() {
        return this.renwor.getWorldRenderer();
    }
}
