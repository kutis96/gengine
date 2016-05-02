package gengine.anim;

import java.awt.Image;

/**
 * Animated frame. Used by the AnimatedImage class to specify animation
 * properties and frame images.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class AnimFrame {

    public final long dt;
    public final Image img;

    /**
     * Constructs the AnimFrame with the given image and frame length.
     *
     * @param img An image corresponding to this frame.
     * @param dt  Dwell time in ms of the current frame.
     */
    public AnimFrame(Image img, long dt) {
        this.img = img;
        this.dt = dt;
    }
}
