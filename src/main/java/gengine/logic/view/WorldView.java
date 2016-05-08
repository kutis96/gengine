package gengine.logic.view;

import gengine.logic.callback.Callback;
import gengine.logic.workers.RenderingWorker;
import gengine.logic.exceptions.ItJustKeepsGoingException;
import gengine.rendering.WorldRenderer;
import gengine.rendering.WorldRendererOptions;
import gengine.util.coords.Coords3D;
import gengine.world.World;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class WorldView extends View {

    private static final Logger LOG = Logger.getLogger(WorldView.class.getName());

    private final World world;
    private final WorldRenderer wren;

    private RenderingWorker renwor;

    private Thread workerThread;

    public WorldView(World world, WorldRenderer wren) {
        this.world = world;
        this.wren = wren;
    }

    @Override
    public void initialize(Callback callback) {

        int targetFPS = 60;

        this.renwor = new RenderingWorker(this, 1000 / targetFPS);
        this.renwor.setWorld(world);
        this.renwor.setWorldRenderer(wren);

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

    public void changeCamPosition(Coords3D pos) {

        WorldRendererOptions wrop = this.renwor.getRendererOptions();
        wrop.setCameraPosition(pos);
        this.renwor.setRendererOptions(wrop);

    }
}
