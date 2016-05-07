package gengine.rendering;

import java.awt.Image;

/**
 * An interface common to all renderable objects.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Renderable {

    /**
     * Render this object to an Image object.
     *
     * @return rendered Image object.
     */
    public Image render();
}
