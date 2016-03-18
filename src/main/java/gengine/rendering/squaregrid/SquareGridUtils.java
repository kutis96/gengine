package gengine.rendering.squaregrid;

import gengine.world.entity.WorldEntity;
import java.util.Comparator;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SquareGridUtils {

    public static class EntityXComparator implements Comparator<WorldEntity> {

        @Override
        public int compare(final WorldEntity a, final WorldEntity b) {
            if (a.getPos().getCoords()[0] < b.getPos().getCoords()[0]) {
                return -1;
            } else if (a.getPos().getCoords()[0] > b.getPos().getCoords()[0]) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static class EntityYComparator implements Comparator<WorldEntity> {

        @Override
        public int compare(final WorldEntity a, final WorldEntity b) {
            if (a.getPos().getCoords()[1] < b.getPos().getCoords()[1]) {
                return -1;
            } else if (a.getPos().getCoords()[1] > b.getPos().getCoords()[1]) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
