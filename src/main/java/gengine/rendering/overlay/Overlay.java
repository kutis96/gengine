package gengine.rendering.overlay;

import gengine.rendering.Renderable;
import gengine.util.coords.Coords3D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Overlay extends Renderable {
    
    public Coords3D getOffset();
}
