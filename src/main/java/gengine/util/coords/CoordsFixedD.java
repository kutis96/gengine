package gengine.util.coords;

import java.util.Arrays;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class CoordsFixedD implements Coords {

    private final int dim;
    private float[] coords;

    public CoordsFixedD(int dim) {
        if (dim > 0) {
            this.dim = dim;
            this.coords = new float[dim];
        } else {
            this.dim = -1;
            this.coords = null;
        }
    }

    public CoordsFixedD(float[] coords) {
        if (coords != null) {
            this.coords = coords;
            this.dim = coords.length;
        } else {
            this.dim = -1;
            this.coords = null;
        }
    }

    public void sanityCheck() throws DimMismatchException {
        if (this.coords == null) {
            throw new DimMismatchException("Can't live with a null coordinate array. Life is hard.");
        }
        if (this.coords.length != this.dim) {
            throw new DimMismatchException("Dimension mismatch: expected " + this.dim + ", found " + coords.length);
        }
    }

    @Override
    public int getDimensions() {
        return this.dim;
    }

    @Override
    public void setCoords(float[] coords) throws DimMismatchException {

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
     */
    public CoordsFixedD add(CoordsFixedD c) {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] + this.getCoords()[i];
            }

            return new CoordsFixedD(newvals);
        } else {
            return null;
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
     */
    public CoordsFixedD subtract(CoordsFixedD c) {
        if (this.dim == c.getDimensions()) {
            float[] newvals = new float[dim];

            for (int i = 0; i < this.dim; i++) {
                newvals[i] = this.coords[i] - this.getCoords()[i];
            }

            return new CoordsFixedD(newvals);
        } else {
            return null;
        }
    }
}
