package gengine.world.entity.hitbox;

import gengine.util.coords.Coords3D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Hitbox {

    /**
     * Returns true whether this hitbox happens to hit another hitbox of an
     * entity/something offset by the given coordinates.
     *
     * @param hitbox A hitbox to check against
     * @param offset An offset to offset the hitbox by
     *
     * @return true when the hitboxes hit, false otherwise.
     */
    public boolean hits(Hitbox hitbox, Coords3D offset);

    /**
     * Returns whether the given coordinate is within the hitbox
     *
     * @param point Coordinate to test against
     *
     * @return true when it is indeed within the hitbox, false otherwise.
     */
    public boolean isWithin(Coords3D point);

    /**
     * Returns an array of the outermost points of this hitbox. Should make
     * determining misses a fair bit easier.
     *
     * @return array of outermost points of the hitbox
     */
    public Coords3D[] getOutermost();

    /**
     * Set the rotation of this hitbox. Apparently useful on rotating objects.
     *
     * @param rot hitbox rotation
     */
    public void setRotation(float rot);

    /**
     * Get the rotation of this hitbox. Apparently useful on rotating objects.
     */
    public float getRotation();
}
