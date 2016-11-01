package gengine.rendering.overlay;

import gengine.rendering.Renderable;
import gengine.util.neco.Neco3D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Overlay extends Renderable {

    public Neco3D getOffset();
}
