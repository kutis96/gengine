package gengine.util.coords;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class CoordsFixedD implements Coords {

    private static final Logger LOG = Logger.getLogger(CoordsFixedD.class.getName());

    private final int dim;
    private float[] coords;

    public CoordsFixedD(int dim) {
        if (dim > 0) {
            this.dim = dim;
            try {
                this.setCoords(new float[dim]);
            } catch (DimMismatchException ex) {
                LOG.severe("DimMismatch in constructor. If you see this, something's really flocked up.");
            } catch (ValueException ex) {
                LOG.severe("ValueException in constructor. If you see this, something's really flocked up.");
            }
        } else {
            this.dim = -1;
            this.coords = null;
        }
    }

    public CoordsFixedD(float[] coords) throws ValueException {
        if (coords != null) {
            this.dim = coords.length;
            try {
                this.setCoords(coords);
            } catch (DimMismatchException ex) {
                LOG.severe("DimMismatch in constructor. If you see this, something's really flocked up.");
            }
        } else {
            this.dim = -1;
            this.coords = null;
        }
    }

    public CoordsFixedD(CoordsFixedD thingtoclone) throws ValueException {
        this.dim = thingtoclone.getDimensions();
        try {
            this.setCoords(thingtoclone.getCoords());
        } catch (DimMismatchException ex) {
            LOG.severe("DimMismatch in constructor. If you see this, something's really flocked up.");
        }
    }

    @Override
    public int getDimensions() {
        return this.dim;
    }

    @Override
    public final void setCoords(float[] coords) throws DimMismatchException, ValueException {
        CoordUtils.validityCheck(coords);
        this.coords = Arrays.copyOf(coords, this.dim);
    }

    @Override
    public float[] getCoords() {
        if (this.coords != null) {
            return Arrays.copyOf(this.coords, this.dim);
        } else {
            return new float[]{};
        }
    }

    @Override
    public String toString() {
        if (this.coords != null) {
            String ret = "(";
            for (int i = 0; i < this.coords.length; i++) {
                if (i != 0) {
                    ret += ",\t";
                }
                ret += Float.toString(this.coords[i]);
            }
            return ret + ")[" + Integer.toString(this.dim) + "]";
        } else {
            return "(null)[-1]";
        }
    }

    /**
     * Adds the given coordinates to this CoordsFixedD coordinates, and creates
     * a new CoordsFixedD with those new coordinates. Useful for handling
     * offsets.
     *
     * @param c Coordinates to add to this object's coordinates.
     *
     * @return New object with those new coordinates. Returns null when the
     *         dimensions don't match.
     *
     * @throws DimMismatchException
     */
    public CoordsFixedD add(CoordsFixedD c) throws DimMismatchException, ValueException {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] + c.getCoords()[i];
            }

            return new CoordsFixedD(newvals);
        } else {
            throw new DimMismatchException();
        }
    }

    /**
     * Subtracts the given coordinates from this CoordsFixedD coordinates, and
     * creates a new CoordsFixedD with those new coordinates. Useful for
     * handling offsets.
     *
     * @param c Coordinates to add to this object's coordinates.
     *
     * @return New object with those new coordinates. Returns null when the
     *         dimensions don't match.
     *
     * @throws DimMismatchException
     */
    public CoordsFixedD subtract(CoordsFixedD c) throws DimMismatchException, ValueException {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] - c.getCoords()[i];
            }

            return new CoordsFixedD(newvals);
        } else {
            throw new DimMismatchException();
        }
    }

    public CoordsFixedD multiply(CoordsFixedD c) throws DimMismatchException, ValueException {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] * c.getCoords()[i];
            }

            return new CoordsFixedD(newvals);
        } else {
            throw new DimMismatchException();
        }
    }

    public CoordsFixedD divide(CoordsFixedD c) throws DimMismatchException, ValueException {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] / c.getCoords()[i];
            }

            return new CoordsFixedD(newvals);
        } else {
            throw new DimMismatchException();
        }
    }

    public void increment(CoordsFixedD c) throws DimMismatchException, ValueException {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] + c.getCoords()[i];
            }

            this.setCoords(newvals);
        } else {
            throw new DimMismatchException();
        }
    }

    public void decrement(CoordsFixedD c) throws DimMismatchException, ValueException {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] - c.getCoords()[i];
            }

            this.setCoords(newvals);
        } else {
            throw new DimMismatchException();
        }
    }

    public void multiply(float f) throws DimMismatchException {
        for (int i = 0; i < this.dim; i++) {
            this.coords[i] *= f;
        }
    }

    /**
     * Returns a vector length of this thing, ye olde Pythagorian way.
     *
     * @return vector length
     */
    public float vecLength() {
        float x = 0;

        for (float f : this.coords) {
            x += f * f;
        }

        return (float) Math.sqrt(x);
    }

    public float distanceTo(CoordsFixedD point) {
        float sum = 0;
        for (int i = 0; i < Math.min(this.getDimensions(), point.getDimensions()); i++) {
            float dif = this.getCoords()[i] - point.getCoords()[i];
            sum += dif * dif;
        }
        return (float) Math.sqrt(sum);
    }
}
