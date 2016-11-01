package gengine.anim;

import java.awt.Image;

/**
 * A class to handle some basic animations.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SimpleAnimatedImage implements AnimImage {

    private final Image[] frames;
    private int currentFrame;
    private int ms_per_frame;
    private long dtSum;

    /**
     * @param frames a bunch of frames to animate
     * @param ms_per_frame milliseconds per frame of animation - 25FPS:
     * 40ms/frame, 50FPS: 20ms/frame, 60FPS: 16.67ms/frame, 100FPS:
     * 10ms/frame...
     */
    public SimpleAnimatedImage(Image[] frames, int ms_per_frame) {
        this.frames = frames;
        this.ms_per_frame = ms_per_frame;
        this.currentFrame = 0;
    }

    /**
     * Gets the current image of the animation.
     *
     * @return current frame
     */
    @Override
    public Image getCurrentImage() {
        if (frames == null || frames.length == 0) {
            return null;
        }

        return frames[currentFrame];
    }

    /**
     * Updates/advances the animation.
     *
     * @param dt time elapsed since the last update in milliseconds
     */
    @Override
    public void tick(long dt) {
        dtSum += dt;

        if (dtSum >= this.ms_per_frame) {

            dtSum = dtSum - this.ms_per_frame;

            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    public int getMsPerFrame() {
        return ms_per_frame;
    }

    public void setMsPerFrame(int ms_per_frame) {
        this.ms_per_frame = ms_per_frame;
    }
}
