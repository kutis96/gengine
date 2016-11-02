package gengine.rendering.overlay;

import gengine.rendering.text.BasicMonoTextRenderer;
import gengine.util.coords.Neco3D;
import java.awt.Image;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public final class TextOverlay implements Overlay {

    private static final Logger LOG = Logger.getLogger(TextOverlay.class.getName());

    private final BasicMonoTextRenderer fd;
    private Image img;

    private Neco3D offset;

    public TextOverlay(String text, Neco3D offset) {
        this.fd = new BasicMonoTextRenderer();
        this.setText(text);

        this.offset = offset;
    }

    @Override
    public Image render() {
        return img;
    }

    @Override
    public Neco3D getOffset() {
        return this.offset;
    }

    public void setOffset(Neco3D offset) {
        this.offset = offset;
    }

    public void setText(String text) {
        this.img = fd.drawString(text);
    }
}
