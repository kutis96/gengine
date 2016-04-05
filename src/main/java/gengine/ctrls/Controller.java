    package gengine.ctrls;

import gengine.rendering.*;
import gengine.util.WorldPack;
import gengine.util.coords.Coords3D;
import gengine.winman.GWindow;
import gengine.world.World;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Controller {

    private static final Logger LOG = Logger.getLogger(Controller.class.getName());
    
    private final GWindow window;
    private final WorldPack wp;

    public Controller(GWindow window, WorldPack wp) {
        this.window = window;
        this.wp = wp;

        this.window.setIgnoreRepaint(true);
    }

    public void tick(long dt) {
        
        this.wp.world.tick(dt);
        
        //TODO: check for collisions etc. here, get events from there and stuff.
    }

    public void render() throws WorldTypeMismatchException, RendererException {
        BufferedImage buf;
        buf = new BufferedImage(this.window.getWidth(), this.window.getHeight(), BufferedImage.TYPE_INT_ARGB);

        WorldRendererOptions wro = new WorldRendererOptions();

        Graphics g = buf.getGraphics();
        
        g.setColor(Color.gray);
        g.fillRect(0, 0, buf.getWidth(), buf.getHeight());

        this.wp.wren.render(this.wp.world, buf, wro);
        
        this.window.getGraphics().drawImage(buf, 0, 0, window);
    }

    public Coords3D mouseToWorldCoords(Point mouse) {
        try {
            return this.wp.wren.getWorldCoords(this.wp.world, mouse);
        } catch (WorldTypeMismatchException | RendererException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //TODO: add mouse event listening and event dispatching

}
