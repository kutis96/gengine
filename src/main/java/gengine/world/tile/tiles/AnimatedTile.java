package gengine.world.tile.tiles;

import gengine.anim.AnimImage;
import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
import java.awt.Image;

/**
 * A tile with an animated texture.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class AnimatedTile extends Tile {

    private final AnimImage ai;

    /**
     * Constructs the AnimatedTile with a specified AnimatedImage as its
     * texture.
     *
     * @param ai the AnimatedImage to use
     */
    public AnimatedTile(AnimImage ai) {
        this.ai = ai;
    }

    /**
     * Ticks (updates) the animation.
     *
     * @param dt Time in milliseconds since last update.
     */
    @Override
    public void tick(long dt) {
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

    @Override
    public boolean canSeeThrough(Coords3D direction, Coords3D offset) {
        return this.isWall();
    }
}
