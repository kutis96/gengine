package gengine.events.generators;

import gengine.util.coords.Neco3D;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class LinearIterator implements Iterator<Neco3D> {

    private static final Logger LOG = Logger.getLogger(LinearIterator.class.getName());

    private Neco3D it;
    private final Neco3D deltaOrig;
    private final Neco3D delta;

    public LinearIterator(Neco3D pos1, Neco3D pos2) {
        this.it = new Neco3D();
        deltaOrig = new Neco3D(pos2.sub(pos1));

        double a = maxAbs(deltaOrig.getFloaty());

        delta = new Neco3D(deltaOrig.multiplyD(1 / a));

        LOG.log(Level.FINE, "pos1:  {0}", pos1);
        LOG.log(Level.FINE, "pos2:  {0}", pos2);
        LOG.log(Level.FINE, "delta: {0}", delta);
    }

    @Override
    public boolean hasNext() {
        return it.vecLength() < deltaOrig.vecLength() + 0.25;
    }

    @Override
    public Neco3D next() {
        if (this.hasNext()) {
            this.it.increment(delta);
            return new Neco3D(this.it);
        } else {
            return null;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private static double maxAbs(double[] fa) {
        double max = Float.MIN_VALUE;

        for (double f : fa) {
            max = (Math.abs(f) > Math.abs(max)) ? f : max;
        }

        return max;
    }

}
