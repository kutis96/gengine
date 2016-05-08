package demo;

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
import gengine.world.TiledWorld;
import gengine.world.entity.PlayerEntity;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
        String tsFile = "/demo/world1/world1.ts";

        LOG.info("Loading Demo World...");

        this.world = TiledWorldLoader.load(
                this.getClass().getResourceAsStream(mapFile));

        try {
            this.world.setTileset(
                    TilesetLoader.load(
                            new File(this.getClass().getResource(tsFile).toURI())));
        } catch (URISyntaxException ex) {
            throw new RuntimeException("Failed to URIfy resource " + tsFile, ex);
        }

        LOG.info("World loaded.");

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
            wcon = new WorldController(world, worldView);
        } catch (WorldTypeMismatchException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new RuntimeException("Mismatched WorldType", ex);
        }

        try {
            this.world.addEntity(new ThePlayer(this.wcon));
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
