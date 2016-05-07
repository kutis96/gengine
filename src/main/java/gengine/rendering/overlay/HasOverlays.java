package gengine.rendering.overlay;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface HasOverlays {

    /**
     * Returns all of the Object's Overlays.
     *
     * @return array of Overlays.
     */
    public Overlay[] getOverlays();

    /**
     * Attach an Overlay to this Object.
     *
     * @param overlay to be attached
     */
    public void attachOverlay(Overlay overlay);

    /**
     * A function to be periodically called to update the Overlays.
     *
     * @param dt time in ms since the last update.
     */
    public void animateOverlays(long dt);
}
