package demo;

import demo.ent.ScaredBall;
import demo.ent.ThePlayer;
import gengine.logic.controller.MainController;
import gengine.logic.controller.WorldController;
import gengine.logic.view.WorldView;
import gengine.rendering.WorldRenderer;
import gengine.rendering.WorldTypeMismatchException;
import gengine.rendering.squaregrid.SquareGridRenderer;
import gengine.util.UnsupportedFormatException;
import gengine.util.loaders.TiledWorldLoader;
import gengine.util.loaders.TilesetLoader;
import gengine.util.coords.Neco3D;
import gengine.world.TiledWorld;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class MainDemoController extends MainController {

    private static final Logger LOG = Logger.getLogger(MainDemoController.class.getName());

    private TiledWorld world;
    private WorldView worldView;
    private final JFrame mainFrame;

    private WorldController wcon;
    private Thread tWcon;

    public MainDemoController(JFrame mainFrame) throws IOException, UnsupportedFormatException {
        this(mainFrame, 20);
    }

    public MainDemoController(JFrame mainFrame, int period) throws IOException, UnsupportedFormatException {
        super(mainFrame, period);
        this.mainFrame = mainFrame;

        String mapFile = "/demo/world1/world1.map";

        String tsBase = "/demo/world1";
        String tsFile = "world1.ts";

        LOG.info("Loading Demo World...");

        this.world = TiledWorldLoader.load(
                this.getClass().getResourceAsStream(mapFile));

        this.world.setTileset(
                TilesetLoader.load(MainDemoController.class.getResourceAsStream(tsBase + "/" + tsFile), tsBase, true));

        LOG.info("World loaded.");
        
        LOG.info(Arrays.toString(this.world.getWorldSize()));

        
        LOG.info("Hello!");
    }

    @Override
    public void init() {

        WorldRenderer renderer = new SquareGridRenderer(64);

        this.worldView = new WorldView(world, renderer);

        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.add(worldView, BorderLayout.CENTER);

        this.worldView.setVisible(true);
        this.mainFrame.setVisible(true);
        this.mainFrame.doLayout();

        try {
            wcon = new WorldController(this.world, this.worldView);
        } catch (WorldTypeMismatchException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new RuntimeException("Mismatched WorldType", ex);
        }

        try {
            int[] wsiz = this.world.getWorldSize();

            this.world.addEntity(new ThePlayer(this.wcon));

            for (int i = 0; i < 50; i++) {
                ScaredBall sb = new ScaredBall(wcon);
                sb.setPos(new Neco3D(
                        new double[]{
                            wsiz[0] * Math.random(),
                            wsiz[1] * Math.random(),
                            0
                        }
                ));
                this.world.addEntity(sb);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error loading Entities!", ex);
        }

        LOG.info("Added entities.");

        wcon.start();
    }

    @Override
    public void work(long dt) {
        //do stuff
    }

    @Override
    public void die() {
        this.wcon.stop();
    }

}
