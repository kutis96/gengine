package gengine.rendering.squaregrid;

import gengine.util.coords.Coords3D;
import gengine.world.entity.WorldEntity;
import java.util.Comparator;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SquareGridUtils {

    private SquareGridUtils() {
    }

    /**
     * Enum for specifying in which direction to sort etc.
     */
    public static enum Axis {

        X,
        Y,
        Z
    }

    /**
     * Comparator to compare X/Y/Z coordinates of WorldEntities; useful for
     * sorting.
     */
    public static class EntityAxisComparator implements Comparator<WorldEntity> {

        private final int dir;

        public EntityAxisComparator(Axis dir) {
            this.dir = dir.ordinal();
        }

        @Override
        public int compare(final WorldEntity a, final WorldEntity b) {
            if (a.getPos().getCoords()[dir] < b.getPos().getCoords()[dir]) {
                return -1;
            } else if (a.getPos().getCoords()[dir] > b.getPos().getCoords()[dir]) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Checks whether a given point is within a specified rectangular area.
     *
     * @param point  Point to check
     * @param lower  The lower of those recrangle coordinates (aka top left)
     * @param higher The higher of those rectangle coordinates (aka bottom
     *               right)
     *
     * @return True when a specified coordinate is indeed within the specified
     *         rectangle, false otherwise.
     */
    public static boolean isWithin(Coords3D point, Coords3D lower, Coords3D higher) {
        //I figured this would be a fair bit faster than looping through the actual coordinates with a silly loop
        //but CodeQuality happened. grumble grumble.

        for (int i = 0; i < 3; i++) {
            if (!(lower.getCoords()[i] <= point.getCoords()[i]
                    && point.getCoords()[i] <= higher.getCoords()[i])) {

                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether a given point is within a specified rectangular area. This
     * function ignores the Z coordinate.
     *
     * @param point  Point to check
     * @param lower  The lower of those recrangle coordinates (aka top left)
     * @param higher The higher of those rectangle coordinates (aka bottom
     *               right)
     *
     * @return True when a specified coordinate is indeed within the specified
     *         rectangle, false otherwise.
     */
    public static boolean isWithinIgnoringZ(Coords3D point, Coords3D lower, Coords3D higher) {
        //I figured this would be a fair bit faster than looping through the actual coordinates with a silly loop 
        return (lower.getX() <= point.getX() && point.getX() <= higher.getX())
                && (lower.getY() <= point.getY() && point.getY() <= higher.getY());
    }

    /**
     * Comparator designed for first sorting by the X axis and then by Z axis.
     * This one is probably only specific to the SquareGridRenderer.
     */
    public static class EntityZYComparator implements Comparator<WorldEntity> {

        @Override
        public int compare(final WorldEntity a, final WorldEntity b) {
            Coords3D ac = (Coords3D) a.getPos();
            Coords3D bc = (Coords3D) b.getPos();

            if ((int) ac.getZ() == (int) bc.getZ()) {

                if (ac.getY() < bc.getY()) {
                    return -1;
                } else if (ac.getY() > bc.getY()) {
                    return 1;
                } else {
                    return 0;
                }
            }

            if (ac.getZ() < bc.getZ()) {
                return -1;
            } else if (ac.getZ() > bc.getZ()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
