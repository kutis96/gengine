package gengine.winman;

import gengine.util.coords.Coords2D;
import java.awt.Canvas;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public abstract class GSurface extends Canvas {
    /**
     * Returns true if 
     * @param point
     * @return 
     */
    public abstract boolean hits(Coords2D point);
}
