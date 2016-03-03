package gengine.tile;

import gengine.anim.AnimatedImage;
import gengine.util.coords.Coords;
import java.awt.Image;

/**
 * A tile with an animated texture.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class AnimatedTile extends Tile {

    private final AnimatedImage ai;

    /**
     * Constructs the AnimatedTile with a specified AnimatedImage as its
     * texture.
     *
     * @param pos tile's coordinates in the world
     * @param ai the AnimatedImage to use
     */
    public AnimatedTile(Coords pos, AnimatedImage ai) {
        super(pos);
        this.ai = ai;
    }

    /**
     * Ticks (updates) the animation.
     *
     * @param iw See Tile documentation, not actually used here.
     * @param dt Time in milliseconds since last update.
     */
    @Override
    public void tick(TiledWorld iw, long dt) {
        ai.tick(dt);
    }

    /**
     * Returns the current frame image.
     *
     * @return current frame of the AnimatedTile's animation.
     */
    @Override
    public Image render() {
        return ai.getCurrentImage();
    }
}
