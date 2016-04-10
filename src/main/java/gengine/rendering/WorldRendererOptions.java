package gengine.rendering;

import gengine.util.coords.Coords3D;

/**
 * A container used to store various options for the Renderers.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WorldRendererOptions {

    private Coords3D cameraOffset = new Coords3D();

    private Coords3D cameraPosition = new Coords3D();

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
    public Coords3D getCameraOffset() {
        return cameraOffset;
    }

    public void setCameraOffset(Coords3D cameraOffset) {
        this.cameraOffset = cameraOffset;
    }
    //END OF CAMERA OFFSET IO

    //CAMERA POS IO
    public Coords3D getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(Coords3D cameraPosition) {
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

        WARN_ON_MISSING_TILES,
        DIE_ON_MISSING_TILES,
        NO_ENTITIES,
        NO_TILES;
        
        public long getFlagValue() {            
            return 1 << this.ordinal();
        }
    }
}
