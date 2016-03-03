package gengine.anim;

import java.awt.Image;

/**
 * A class to handle some basic animations.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class AnimatedImage {

    private final AnimFrame[] frames;
    private int current_frame;
    private long sum_dt;

    /**
     * @param frames a bunch of frames to animate
     */
    public AnimatedImage(AnimFrame[] frames) {
        this.frames = frames;
        this.current_frame = 0;
    }

    /**
     * Gets the current image of the animation.
     *
     * @return current frame
     */
    public Image getCurrentImage() {
        if (frames == null || frames.length == 0) {
            return null;
        }

        return frames[current_frame].img;
    }

    /**
     * Updates/advances the animation.
     *
     * @param dt time elapsed since the last update in milliseconds
     */
    public void tick(long dt) {
        sum_dt += dt;

        if (sum_dt >= this.frames[current_frame].dt) {

            sum_dt = sum_dt - this.frames[current_frame].dt;

            current_frame = (current_frame + 1) % frames.length;
        }
    }

}
