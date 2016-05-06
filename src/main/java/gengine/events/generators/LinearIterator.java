package gengine.events.generators;

import gengine.util.coords.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class LinearIterator implements Iterator<Coords3D> {

    private static final Logger LOG = Logger.getLogger(LinearIterator.class.getName());

    private Coords3D it;
    private final Coords3D deltaOrig;
    private final Coords3D delta;

    public LinearIterator(Coords3D pos1, Coords3D pos2) throws DimMismatchException, ValueException {
        try {
            this.it = new Coords3D();
            deltaOrig = new Coords3D(pos2.subtract(pos1));
            
            float a = maxAbs(deltaOrig.getCoords());

            delta = new Coords3D(deltaOrig.multiply(1 / a));
            
            LOG.log(Level.FINE, "pos1:  {0}", pos1);
            LOG.log(Level.FINE, "pos2:  {0}", pos2);
            LOG.log(Level.FINE, "delta: {0}", delta);
            
        } catch (DimMismatchException | ValueException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public boolean hasNext() {
        return it.vecLength() < deltaOrig.vecLength() + 0.25;
    }

    @Override
    public Coords3D next() {
        if (this.hasNext()) {
            try {
                this.it.increment(delta);
                return new Coords3D(this.it);
            } catch (DimMismatchException | ValueException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private static float maxAbs(float[] fa) {
        float max = Float.MIN_VALUE;

        for (float f : fa) {
            max = (Math.abs(f) > Math.abs(max)) ? f : max;
        }

        return max;
    }

}
