package gengine.util.coords.coordutils;

import gengine.util.coords.CoordsFixedD;
import gengine.util.coords.DimMismatchException;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class CoordUtils {

    /**
     * Checks whether a coordinate is inside a bounding box given by the other
     * two coordinates. This assumes box edges being parallel with the base
     * vectors. Coordinate tested exactly on the edges will not count as being
     * inside.
     *
     * @param ct
     * @param c1
     * @param c2
     *
     * @return
     *
     * @throws DimMismatchException Thrown when it just so happens the supplied
     *                              parameters have different dimensions.
     */
    public static boolean isInside(CoordsFixedD ct, CoordsFixedD c1, CoordsFixedD c2) throws DimMismatchException {
        if (ct.getDimensions() != c1.getDimensions() && c1.getDimensions() != c2.getDimensions()) {
            throw new DimMismatchException();   //TODO: add an appropriate message at some point in time
        }
        
        for(int i = 0; i < ct.getDimensions(); i++){
            float t = ct.getCoords()[i];
            float t1 = c1.getCoords()[i];
            float t2 = c2.getCoords()[i];
            
            if(t > t1 && t < t2){
                continue;
            }else{
                return false;
            }
        }
        
        return true;
    }
}
