package gengine.logic.workers;

import gengine.rendering.*;
import gengine.util.Worker;
import gengine.world.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * A Worker Class dedicated to rendering. National heroes right there.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class RenderingWorker extends Worker {

    private static final Logger LOG = Logger.getLogger(RenderingWorker.class.getName());

    // Here's a piece of literature
    //  designed for the Worker Class.
    //
    // ------------------------------------------
    //  Arise ye workers from your slumbers
    //  Arise ye prisoners of want
    //  For reason in revolt now thunders
    //  And at last ends the age of cant.
    //  Away with all your superstitions
    //  Servile masses arise, arise
    //  We’ll change henceforth the old tradition
    //  And spurn the dust to win the prize.
    // ------------------------------------------
    //
    // Cough. To those who have noticed this: I'm sorry,
    // but I just couldn't resist making a bit of fun out of
    // the whole worker class thing. To be quite honest,
    // I'd totally put more of this there, but I ran out
    // of ideas and/or time. Do add some stuff in here in
    // your next commit to have a bit of fun again.
    // I just like puns, okay?
    //
    //
    //
    private World world;
    private WorldRenderer renderer;
    private WorldRendererOptions wrop;
    private final JPanel jp;

    private final Object lock;

    /**
     * Construct a new Rendering Worker class. Let the Workers rise!
     *
     * @param jp     JPanel to render into.
     * @param period Period between each rendering goes in milliseconds.
     */
    public RenderingWorker(JPanel jp, int period) {
        super(period);
        this.jp = jp;
        this.lock = new Object();
    }

    /**
     * Set some new WorldRendererOptions for the WorldRenderer.
     *
     * @param wrop
     */
    public void setRendererOptions(WorldRendererOptions wrop) {
        synchronized (lock) {
            this.wrop = wrop;
        }
    }

    /**
     * Get the assigned WorldRendererOptions for the WorldRenderer.
     *
     * @return the WorldRendererOptions, of course!
     */
    public WorldRendererOptions getRendererOptions() {
        synchronized (lock) {
            return this.wrop;
        }
    }

    /**
     * Give this RenderingWorker a new World to render freely and beautifully.
     *
     * @param world
     */
    public void setWorld(World world) {
        synchronized (lock) {
            this.world = world;
        }
    }

    @Override
    public void init() {

        if (this.world == null) {
            LOG.severe("World not set, I'm outta here.");
            this.stop();
        }
        if (this.renderer == null) {
            LOG.severe("Renderer not set, I'm outta here.");
            this.stop();
        }
        if (this.wrop == null) {
            LOG.info("WROP not set, substituting a default one in instead.");
            this.wrop = new WorldRendererOptions();
        }

        LOG.info("Initialized. " + this.jp.getSize());
    }

    @Override
    public void work(long dt) {
        synchronized (lock) {
            try {
                BufferedImage bi = new BufferedImage(jp.getWidth(), jp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

                Graphics g = bi.getGraphics();
                g.setColor(Color.gray);
                g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

                this.renderer.render(this.world, bi, this.wrop);

                this.jp.getGraphics().drawImage(bi, 0, 0, jp);

            } catch (RendererException ex) {
                LOG.log(Level.SEVERE, null, ex);

                this.stop();    //we're outta here.
            }
        }
    }

    @Override
    public void die() {
        LOG.severe("DEAD.");
    }

    /**
     * Give this RenderingWorker a new WorldRenderer tool to render everything!
     *
     * @param renderer
     */
    public void setWorldRenderer(WorldRenderer renderer) {
        synchronized (lock) {
            this.renderer = renderer;
        }
    }

    /**
     * Get a currently used WorldRenderer.
     *
     * @return currently used WorldRenderer.
     */
    public WorldRenderer getWorldRenderer() {
        synchronized (lock) {
            return this.renderer;
        }
    }

}
