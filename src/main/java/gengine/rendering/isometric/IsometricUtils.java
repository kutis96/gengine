package gengine.rendering.isometric;

import gengine.util.coords.*;

/**
 * This class will (one day) contain various utility functions usable for
 * Isometric world rendering and coordinate sorting.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class IsometricUtils {

    /**
     * Converts isometric coordinates into renderable coordinates.
     *
     * @param isomcoords Coordinates corresponding to those of the
     *                   RenderableWorld
     * @param camoffset  Camera offset in pixels
     * @param campos     Camera position in the world
     * @param tilewidth  Tilewidth.
     * @param zmult      Tileheight.
     *
     * @return 'Graphics' coordinates of the rendered thing.
     */
    public static Coords2D convIsom2Graph(
            Coords3D isomcoords,
            Coords3D camoffset,
            Coords3D campos,
            int tilewidth,
            int zmult
    ) {
        int th = tilewidth / 2;
        int tq = tilewidth / 4;

        int x = (int) isomcoords.getX();
        int y = (int) isomcoords.getY();
        int z = (int) isomcoords.getZ();

        int cx = (int) (th * (x + campos.getX())
                - th * y + camoffset.getX());
        int cy = (int) (th + tq * (y + campos.getY())
                + tq * x
                - zmult * (z + campos.getZ()));

        Coords2D ret = new Coords2D(cx, cy);

        return ret;
    }

    public static Coords2D convIsom2Graph(Coords3D isomcoords, int tilewidth, int zmult) {
        return convIsom2Graph(isomcoords, new Coords3D(0, 0, 0), new Coords3D(0, 0, 0), tilewidth, zmult);
    }

    /**
     * Returns the 'Z depth' of a given coordinate of the IsometricWorld. Useful
     * for rendering entities and stuff.
     *
     * @param coords coordinates
     * @param zmult  Z-multiplier, preferrably tileheight/tilewidth
     *
     * @return Z-depth of the supplied coordinate.
     */
    public static float getIsomZ(Coords3D coords, float zmult) {

        /*
         ____ 0 0
         ___ / . \
         __ x . . y    
         _ / . . . \   
         _/ . . . . \  
         _ . . . . .   |
         __ . . . .    |
         ___ . . .    \|/iz
         ____ . .  
         _____ .          
         */
        return coords.getX() + coords.getY() - zmult * coords.getZ();
    }

}
