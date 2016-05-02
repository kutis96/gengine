package gengine.util.coords;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 * A test class testing the CoordsFixedD class.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class CoordsFixedDTest {

    public CoordsFixedDTest() {
        
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void constructorTest() throws ValueException {

        CoordsFixedD a;
        CoordsFixedD b;
        CoordsFixedD c;

        a = new CoordsFixedD(5);
        b = new CoordsFixedD(new float[]{1, 2, 3, 4, 5});
        c = new CoordsFixedD(b);

        assertArrayEquals("New coords known value", new float[]{0, 0, 0, 0, 0}, a.getCoords(), (float) 0.00001);
        assertArrayEquals("Cloning coords fails", b.getCoords(), c.getCoords(), (float) 0.00001);

        assertEquals("Wrong dimension", 5, a.getDimensions());
        assertEquals("Wrong dimension", 5, b.getDimensions());
        assertEquals("Wrong dimension", 5, c.getDimensions());

    }

    //TODO: add test for nonsensical values returned by the arithmetics
    @Test
    public void nonsensicalValueAssignmentTest() throws DimMismatchException, ValueException {

        float[] bs = new float[]{0, 1, -1, Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY};

        exception.expect(ValueException.class);
        CoordsFixedD a = new CoordsFixedD(new float[]{0, 1, -1, Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY});

        CoordsFixedD b = new CoordsFixedD(bs.length);

        exception.expect(ValueException.class);
        b.setCoords(bs);
    }

    @Test
    public void arithmeticsTest() throws DimMismatchException, ValueException {
        int dim = 50;

        float[] fa = new float[dim];
        float[] fb = new float[dim];

        float[] uniop = new float[dim];

        //bilateral operations
        float[] fsum = new float[dim];
        float[] fdif = new float[dim];
        float[] fmul = new float[dim];
        float[] fdiv = new float[dim];

        //unilateral
        float[] fmuli = new float[dim];
        float[] finc = new float[dim];
        float[] fdec = new float[dim];

        for (int i = 0; i < dim; i++) {
            fa[i] = (float) (Math.random() * 20 - 10);
            fb[i] = (float) (Math.random() * 20 - 10);
            uniop[i] = (float) (Math.random() * 20 - 10);

            //bilateral
            fsum[i] = fa[i] + fb[i];
            fdif[i] = fa[i] - fb[i];
            fmul[i] = fa[i] * fb[i];
            fdiv[i] = fa[i] / fb[i];

            //unilateral
            fmuli[i] = fa[i] * -5;
            finc[i] = fa[i] + uniop[i];
            fdec[i] = fa[i] - uniop[i];
        }

        CoordsFixedD a = new CoordsFixedD(fa);
        CoordsFixedD b = new CoordsFixedD(fb);

        {   //Assignments
            assertArrayEquals("Assignment failed", fa, a.getCoords(), (float) 0.00001);
            assertArrayEquals("Assignment failed", fb, b.getCoords(), (float) 0.00001);
        }
        {   //Sum
            CoordsFixedD sum = a.add(b);
            assertArrayEquals("Addition failed", fsum, sum.getCoords(), (float) 0.00001);
        }
        {   //Difference
            CoordsFixedD dif = a.subtract(b);
            assertArrayEquals("Subtraction failed", fdif, dif.getCoords(), (float) 0.00001);
        }
        {   //Multiplication of two coordinates
            CoordsFixedD mul = a.multiply(b);
            assertArrayEquals("Multiplication failed", fmul, mul.getCoords(), (float) 0.00001);
        }
        {   //Division of two coordinates
            CoordsFixedD div = a.divide(b);
            assertArrayEquals("Division failed", fdiv, div.getCoords(), (float) 0.00001);
        }
        {   //Multiplication by constant
            CoordsFixedD muli = new CoordsFixedD(a);
            muli.multiply(-5);
            assertArrayEquals("Multiplication by constant failed", fmuli, muli.getCoords(), (float) 0.00001);
        }
        {   //Incrementation
            CoordsFixedD inc = new CoordsFixedD(a);
            inc.increment(new CoordsFixedD(uniop));
            assertArrayEquals("Incrementation failed", finc, inc.getCoords(), (float) 0.00001);
        }
        {   //Decrementation
            CoordsFixedD dec = new CoordsFixedD(a);
            dec.decrement(new CoordsFixedD(uniop));
            assertArrayEquals("Multiplication by constant failed", fdec, dec.getCoords(), (float) 0.00001);
        }
        {   //Division by zero
            CoordsFixedD xa = new CoordsFixedD(a);
            CoordsFixedD xb = new CoordsFixedD(xa.getDimensions());

            exception.expect(ValueException.class);
            CoordsFixedD div = xa.divide(xb);
        }
    }
}
