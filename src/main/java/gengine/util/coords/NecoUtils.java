package gengine.util.neco;

import static gengine.util.neco.Neco3D.*;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class NecoUtils {

    public static Neco3D generateRandom(double magnitude) {
        return generateRandom(ZERO, EYE.multiply(magnitude));
    }

    public static Neco3D generateRandom(Neco3D offset, Neco3D magnitude) {
        int[] nxyz = new int[3];

        for (int i = 0; i < 3; i++) {
            nxyz[i] = (int) (Math.random() * magnitude.xyz[i]) + offset.xyz[i];
        }

        return new Neco3D(nxyz, false);
    }

    public static Neco3D directionVector(Neco3D from, Neco3D to) {
        Neco3D delta = to.sub(from);
        return delta.normalize();
    }

    public static double distanceBetween(Neco3D a, Neco3D b){
        return a.sub(b).vecLength();
    }
    
    public static boolean isInside(Neco3D what, Neco3D lower, Neco3D upper) {
        for (int i = 0; i < 3; i++) {
            if (lower.xyz[i] < what.xyz[i] && what.xyz[i] < upper.xyz[i]) {
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

}
