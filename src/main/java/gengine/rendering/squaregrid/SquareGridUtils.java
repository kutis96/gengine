package gengine.rendering.squaregrid;

import gengine.rendering.RenderableContainer;
import gengine.util.coords.Neco3D;
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
            if (a.getPos().getInternalVector()[dir] < b.getPos().getInternalVector()[dir]) {
                return -1;
            } else if (a.getPos().getInternalVector()[dir] > b.getPos().getInternalVector()[dir]) {
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
    public static boolean isWithin(Neco3D point, Neco3D lower, Neco3D higher) {
        //I figured this would be a fair bit faster than looping through the actual coordinates with a silly loop
        //but CodeQuality happened. grumble grumble.

        for (int i = 0; i < 3; i++) {
            if (!(lower.getInternalVector()[i] <= point.getInternalVector()[i]
                    && point.getInternalVector()[i] <= higher.getInternalVector()[i])) {

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
    public static boolean isWithinIgnoringZ(Neco3D point, Neco3D lower, Neco3D higher) {
        //I figured this would be a fair bit faster than looping through the actual coordinates with a silly loop 
        return (lower.getXint() <= point.getXint() && point.getXint() <= higher.getXint())
                && (lower.getYint() <= point.getYint() && point.getYint() <= higher.getYint());
    }

    /**
     * Comparator designed for first sorting by the X axis and then by Z axis.
     * This one is probably only specific to the SquareGridRenderer.
     */
    public static class RContainerZYComparator implements Comparator<RenderableContainer> {

        @Override
        public int compare(final RenderableContainer a, RenderableContainer b) {
            Neco3D ac = a.pos;
            Neco3D bc = b.pos;
            
            if (ac.getZint() == bc.getZint()) {

                if (ac.getYint() < bc.getYint()) {
                    return -1;
                } else if (ac.getYint() > bc.getYint()) {
                    return 1;
                } else {
                    return 0;
                }
            }

            if (ac.getZint() < bc.getZint()) {
                return -1;
            } else if (ac.getZint() > bc.getZint()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
