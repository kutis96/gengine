package gengine.util.coords;

/**
 * An interface used to specify various world coordinates.
 * 
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Coords {    
    int getDimensions();
    void setCoords(int[] ic);
    void setCoords(double[] dc);
    int[] getCoordsI();
    double[] getCoordsD();
}
