package gengine.rendering.overlay;

import gengine.rendering.overlay.text.FontDrawer;
import gengine.util.coords.Coords3D;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TextOverlay implements Overlay {

    private static final Logger LOG = Logger.getLogger(TextOverlay.class.getName());

    private final FontDrawer fd;
    private final Image img;

    private Coords3D offset;

    public TextOverlay(String text, Coords3D offset) {
        try {
            this.fd = new FontDrawer();
            img = fd.drawString(text);
            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

        this.offset = offset;
    }

    @Override
    public Image render() {
        return img;
    }

    @Override
    public Coords3D getOffset() {
        return null;
    }

    public void setOffset(Coords3D offset) {
        this.offset = offset;
    }
}
