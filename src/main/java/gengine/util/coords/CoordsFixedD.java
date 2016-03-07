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

    public float[] getRaw() {
        return coords;
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
            return null;
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
                ret += this.coords[i];
            }
            return ret + ")[" + this.dim + "]";
        } else {
            return "(null)[-1]";
        }
    }

}
