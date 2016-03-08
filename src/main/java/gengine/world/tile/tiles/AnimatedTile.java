package gengine.world.tile.tiles;

import gengine.world.TiledWorld;
import gengine.anim.AnimatedImage;
import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
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
     * @param ai the AnimatedImage to use
     */
    public AnimatedTile(AnimatedImage ai) {
        this.ai = ai;
    }

    /**
     * Ticks (updates) the animation.
     *
     * @param iw See Tile documentation, not used here.
     * @param tilepos See Tile documentation, not used here.
     * @param dt Time in milliseconds since last update.
     */
    @Override
    public void tick(TiledWorld iw, Coords3D tilepos, long dt) {
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
