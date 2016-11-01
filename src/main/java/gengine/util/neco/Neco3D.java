package gengine.util.neco;

import java.util.Arrays;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Neco3D {

    public static Neco3D ZERO = new Neco3D(new int[]{0, 0, 0});
    public static Neco3D EYE = new Neco3D(new int[]{1, 1, 1}).multiply(Neco3D.N_PER_UNIT);

    public static int N_PER_UNIT = 10000; //this sets the granularity

    protected volatile int[] xyz = new int[]{0, 0, 0}; //Neco3D vecvector itself

    private Neco3D(int[] intvec) {
        this.xyz = intvec;
    }

    public Neco3D() {
        //
        this.xyz = new int[]{0, 0, 0};
    }

    /**
     * Constructor suitable for making a copy of a Neco3D vector.
     *
     * @param vec Vector to copy.
     */
    public Neco3D(Neco3D vec) {
        this.xyz = vec.getInternalVector();
    }

    public Neco3D(double[] dvec) {
        for (int i = 0; i < Math.min(dvec.length, 3); i++) {
            this.xyz[i] = (int) (dvec[i] / N_PER_UNIT);
        }
    }

    public Neco3D(int[] ivec, boolean adjust) {
        for (int i = 0; i < Math.min(ivec.length, 3); i++) {
            this.xyz[i] = ivec[i] * ((adjust) ? N_PER_UNIT : 1);
        }
    }

    /**
     *
     * @return Returns a copy of the internal storage array.
     */
    public int[] getInternalVector() {
        return Arrays.copyOf(this.xyz, this.xyz.length);
    }

    public void increment(Neco3D vec) {
        for (int i = 0; i < 3; i++) {
            this.xyz[i] += vec.xyz[i];
        }
    }

    public void decrement(Neco3D vec) {
        for (int i = 0; i < 3; i++) {
            this.xyz[i] -= vec.xyz[i];
        }
    }

    public Neco3D add(Neco3D vec) {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = this.xyz[i] + vec.xyz[i];
        }

        return new Neco3D(nxyz);
    }

    public Neco3D sub(Neco3D vec) {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = this.xyz[i] - vec.xyz[i];
        }

        return new Neco3D(nxyz);
    }

    public Neco3D multiply(int x) {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = (int) nxyz[i] * x;
        }

        return new Neco3D(nxyz);
    }

    public Neco3D multiply(double d) {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = (int) (nxyz[i] * d);
        }

        return new Neco3D(nxyz);
    }

    public Neco3D divide(double d) {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = (int) (nxyz[i] / d);
        }

        return new Neco3D(nxyz);
    }

    public double vecLength() {
        long sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += this.xyz[i] * this.xyz[i];
        }

        return Math.sqrt(sum);
    }

    public boolean equals(Neco3D thing) {
        for (int i = 0; i < 3; i++) {
            if (this.xyz[i] != thing.xyz[i]) {
                return false;
            }
        }

        return true;
    }

    public Neco3D normalize() {
        if (this.equals(ZERO)) {
            return ZERO;
        }

        return new Neco3D(this).divide(this.vecLength());
    }

    public int[] getAdjusted() {
        int[] rxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            rxyz[i] = (int) (this.xyz[i] / N_PER_UNIT);
        }

        return rxyz;
    }

    public double[] getFloaty() {
        double[] dxyz = new double[3];

        for (int i = 0; i < 3; i++) {
            dxyz[i] = this.xyz[i] / (double) N_PER_UNIT;
        }

        return dxyz;
    }

    public double getX() {
        return this.xyz[0] / (double) N_PER_UNIT;
    }

    public double getY() {
        return this.xyz[1] / (double) N_PER_UNIT;
    }

    public double getZ() {
        return this.xyz[2] / (double) N_PER_UNIT;
    }
    
    public int getXint() {return this.xyz[0];}
    public int getYint() {return this.xyz[1];}
    public int getZint() {return this.xyz[2];}

    @Override
    public String toString() {
        return "[" + this.getX() + ", " + this.getY() + ", " + this.getZ() + "]";
    }

    public Neco3D roundAll() {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = this.xyz[i] - (this.xyz[i] % N_PER_UNIT);
        }

        return new Neco3D(nxyz);
    }
}
