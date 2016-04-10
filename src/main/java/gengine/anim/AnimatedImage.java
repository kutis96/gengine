package gengine.anim;

import java.awt.Image;

/**
 * A class to handle some basic animations.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class AnimatedImage {

    private final AnimFrame[] frames;
    private int currentFrame;
    private long dtSum;

    /**
     * @param frames a bunch of frames to animate
     */
    public AnimatedImage(AnimFrame[] frames) {
        this.frames = frames;
        this.currentFrame = 0;
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

        return frames[currentFrame].img;
    }

    /**
     * Updates/advances the animation.
     *
     * @param dt time elapsed since the last update in milliseconds
     */
    public void tick(long dt) {
        dtSum += dt;

        if (dtSum >= this.frames[currentFrame].dt) {

            dtSum = dtSum - this.frames[currentFrame].dt;

            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

}
