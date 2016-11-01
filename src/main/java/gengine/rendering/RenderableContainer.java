package gengine.rendering;

import gengine.util.neco.Neco3D;

/**
 * A container containing a Renderable and its position.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class RenderableContainer {

    public final Neco3D pos;
    public final Renderable ren;

    public RenderableContainer(Neco3D position, Renderable renderable) {
        this.pos = position;
        this.ren = renderable;
    }
    
    @Override
    public String toString() {
        return ren.toString() + " @ " + pos.toString();
    }
}
