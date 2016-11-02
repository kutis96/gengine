package gengine.rendering;

import gengine.util.coords.Neco3D;

/**
 * A container used to store various options for the Renderers.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WorldRendererOptions {

    private Neco3D cameraOffset = new Neco3D();

    private Neco3D cameraPosition = new Neco3D();

    private double zoom = 1;

    private long fl = Flags.DIE_ON_MISSING_TILES.getFlagValue();
    
    //FLAG HANDLING
    public boolean hasFlag(Flags flag){
        return (fl & flag.getFlagValue()) != 0;
    }
    
    public void addFlag(Flags flag){
        this.fl |= flag.getFlagValue();
    }
    
    public void removeFlag(Flags flag){
        this.fl &= ~flag.getFlagValue();
    }
    //END OF FLAG HANDLING
    
    //CAMERA OFFSET IO
    public Neco3D getCameraOffset() {
        return cameraOffset;
    }

    public void setCameraOffset(Neco3D cameraOffset) {
        this.cameraOffset = cameraOffset;
    }
    //END OF CAMERA OFFSET IO

    //CAMERA POS IO
    public Neco3D getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(Neco3D cameraPosition) {
        this.cameraPosition = cameraPosition;
    }
    //END OF CAMERA POS IO

    //ZOOM IO
    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    //END OF ZOOM IO
    
    public static enum Flags {

        DEBUGMODE,
        WARN_ON_MISSING_TILES,
        DIE_ON_MISSING_TILES,
        NO_ENTITIES,
        NO_TILES;
        
        public long getFlagValue() {            
            return 1 << this.ordinal();
        }
    }
}
