package gengine.rendering;

import gengine.util.coords.Coords3D;

/**
 * A container containing a Renderable and its position.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class RenderableContainer {

    public final Coords3D pos;
    public final Renderable ren;

    public RenderableContainer(Coords3D position, Renderable renderable) {
        this.pos = position;
        this.ren = renderable;
    }
    
    @Override
    public String toString() {
        return ren.toString() + " @ " + pos.toString();
    }
}
