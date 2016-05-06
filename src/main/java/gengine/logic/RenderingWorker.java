package gengine.logic;

import gengine.rendering.*;
import gengine.util.Worker;
import gengine.world.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

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
    private final JFrame jf;

    /**
     * Construct a new Rendering Worker class. Let the Workers rise!
     *
     * @param jf     JFrame to render into.
     * @param period Period between each rendering goes in milliseconds.
     */
    public RenderingWorker(JFrame jf, int period) {
        super(period);
        this.jf = jf;
    }

    /**
     * Set some new WorldRendererOptions for the WorldRenderer.
     *
     * @param wrop
     */
    public synchronized void setRendererOptions(WorldRendererOptions wrop) {
        this.wrop = wrop;
    }

    /**
     * Get the assigned WorldRendererOptions for the WorldRenderer.
     *
     * @return the WorldRendererOptions, of course!
     */
    public synchronized WorldRendererOptions getRendererOptions() {
        return this.wrop;
    }

    /**
     * Give this RenderingWorker a new World to render freely and beautifully.
     *
     * @param world
     */
    public synchronized void setWorld(World world) {
        this.world = world;
    }

    /**
     * Give this RenderingWorker a new WorldRenderer tool to render everything!
     *
     * @param renderer
     */
    public synchronized void setWorldRenderer(WorldRenderer renderer) {
        this.renderer = renderer;
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

        Graphics g = this.jf.getGraphics();

        g.setColor(Color.gray);

        g.fillRect(0, 0, jf.getWidth(), jf.getHeight());
        
        LOG.info("Initialized.");
    }

    @Override
    public void work(long dt) {

        try {
            BufferedImage bi = new BufferedImage(jf.getWidth(), jf.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            
            Graphics g = bi.getGraphics();
            g.setColor(Color.gray);
            g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
            
            
            this.renderer.render(this.world, bi, this.wrop);
            
            this.jf.getGraphics().drawImage(bi, 0, 0, jf);

        } catch (RendererException ex) {
            LOG.log(Level.SEVERE, null, ex);

            this.stop();    //we're outta here.
        }
    }

    @Override
    public void die() {
        LOG.severe("DEAD.");
    }

}
