package gengine.cont.rendering;

import gengine.util.Coords3D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class RendererOptions {
    Coords3D cameraOffset;
    double zoom;
    
    /**
     * Sets up the RenderedOptions with default values.
     */
    public RendererOptions(){
        this.cameraOffset = new Coords3D(0, 0, 0);
        this.zoom = 1;
    }
}
