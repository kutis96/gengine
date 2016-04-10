package gengine.rendering;

import gengine.util.coords.Coords3D;

/**
 * A container used to store various options for the Renderers.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WorldRendererOptions {

    public Coords3D cameraOffset = new Coords3D(0, 0, 0);

    public Coords3D cameraPosition = new Coords3D(0, 0, 0);

    public double zoom = 1;

    public long flags = RenderFlags.DIE_ON_MISSING_TILES.getFlagValue();
    
    public static enum RenderFlags {

        WARN_ON_MISSING_TILES,
        DIE_ON_MISSING_TILES,
        NO_ENTITIES,
        NO_TILES,
        ANTIALIAS;
        
        public long getFlagValue() {
            return 1 << this.ordinal();
        }
    }
}
