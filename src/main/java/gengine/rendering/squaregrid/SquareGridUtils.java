package gengine.rendering.squaregrid;

import gengine.world.entity.WorldEntity;
import java.util.Comparator;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SquareGridUtils {

    public SquareGridUtils() {
    }

    public static enum Direction {
        X,
        Y,
        Z
    }

    public static class EntityXYZComparator implements Comparator<WorldEntity> {

        private final int dir;

        public EntityXYZComparator(Direction dir) {
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
}
