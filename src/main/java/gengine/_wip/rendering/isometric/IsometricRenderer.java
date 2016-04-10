package gengine._wip.rendering.isometric;

import gengine.rendering.*;
import gengine.rendering.WorldRendererOptions.Flags;
import gengine.util.coords.Coords2D;
import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.tile.TilesetIndexException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class IsometricRenderer implements WorldRenderer {

    private static final Logger LOG = Logger.getLogger(IsometricRenderer.class.getName());

    private final int tilewidth;

    /**
     * Constructs the IsometricRenderer with default values.
     */
    public IsometricRenderer() {
        this.tilewidth = 120;   //default tile width
    }

    /**
     * Constructs the IsometricRenderer with given parameters.
     *
     * @param tilewidth default isometric tile width
     */
    public IsometricRenderer(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    /**
     * Renders the TiledWorld into a specified BufferedImage object with given
     * WorldRendererOptions. May throw an exception when supplied some other
     * World.
     *
     * @param world World to render
     * @param bi    BufferedImage to render onto
     * @param ro    WorldRendererOptions for rendering
     *
     * @throws RendererException Thrown when the supplied World happens to be
     *                           something other than TileWorld, or on any other
     *                           occassion. I would've thrown something else
     *                           here as well, but apparently that's a MAJOR
     *                           PROBLEM, according to CodeQuality.
     */
    @Override
    public void render(World world, BufferedImage bi, WorldRendererOptions ro) throws RendererException {
        if (world instanceof TiledWorld) {
            this.render((TiledWorld) world, bi, ro);
        } else {
            throw new RendererException("Invalid World Type Detected!");
        }
    }

    /**
     * Renders the TiledWorld into a specified BufferedImage object with given
     * WorldRendererOptions.
     *
     * @param world   TiledWorld to render.
     * @param surface BufferedImage object to render onto.
     * @param wrop    WorldRendererOptions supplying all the renderer options!
     */
    private void render(TiledWorld world, BufferedImage surface, WorldRendererOptions wrop) throws RendererException {

        //TODO:
        //----------------------------------------------------------------------
        // - add clickmap rendering (opt?) -- to be done waaay later on
        //     ^ can be solved with a tiny bit of math.
        // - visibility prediction, so one doesn't need to render the whole world all the time, but only the visible portion
        // - end up using something a bit more universal at some point
        //     ^ soon.
        //
        // - add entity rendering
        //
        // - May be nice to create a WorldRenderer interface for World rendererers.
        //
        // WIP:
        //----------------------------------------------------------------------
        // - refactor - everyone loves refactoring!
        //
        // DONE:
        //----------------------------------------------------------------------
        // - modify the rendering so it actually renders by rows (back to front) instead of the 'XY sweep'
        //   ^ I HAVE NO IDEA WHY THE HELL DID I WANT TO DO THAT
        //
        if (wrop == null) {
            wrop = new WorldRendererOptions();
        }

        surface.setAccelerationPriority(1);

        Graphics2D g = surface.createGraphics();

        g.setBackground(Color.BLACK);   //blanking

        //Size of tiles in pixels, scaled by zoom
        int tilesize = (int) (this.tilewidth * wrop.getZoom());

        //Gets the current worldsize
        Coords3D wrldsize = world.getWorldSize();

        WorldIterator it = new WorldIterator(world.getWorldSize());

        while (it.hasNext()) {
            Coords3D coords = it.next();

            try {
                Tile currentTile = world.getWorldtile(coords);

                if (currentTile != null) {

                    Image renderedTile = currentTile.render();

                    if (renderedTile == null) {
                        continue;
                    }

                    //Scaling ratio of the tiles.
                    //  calculated on the horizontal axis
                    double scaling = tilesize / (double) renderedTile.getWidth(null);

                    //Converts isometric coordinates into graphic coordinates (aka pixel coordinates on the screen
                    Coords2D conv = IsometricUtils.convIsom2Graph(coords, wrop.getCameraOffset(), wrop.getCameraPosition(),
                            tilesize,
                            3 * tilesize / 4);

                    //draw image on the rendered surface
                    g.drawImage(
                            //Image to draw
                            renderedTile,
                            //position gotten from the converter
                            (int) conv.getX(),
                            (int) (conv.getY() - renderedTile.getHeight(null) * scaling),
                            tilesize, //Width
                            (int) (renderedTile.getHeight(null) * scaling), //Height
                            null //Observer
                    );
                }
            } catch (TilesetIndexException ex) {
                String message = "Found a tile with an invalid index while rendering!\n\t" + ex.getMessage();

                LOG.log(Level.WARNING, message);
                LOG.log(Level.INFO, ex.getMessage());

                if (wrop.hasFlag(Flags.DIE_ON_MISSING_TILES)) {
                    throw (RendererException) (Exception) ex;   //<>< -y
                }
            }
        }

        Coords2D camaimpos = IsometricUtils.convIsom2Graph(new Coords3D(0, 0, 0), wrop.getCameraOffset(), wrop.getCameraPosition(),
                tilesize,
                3 * tilesize / 4);

        g.drawRect((int) camaimpos.getX(), (int) camaimpos.getY(), -tilesize, -tilesize / 2);

        g.dispose();
    }

    private static class WorldIterator implements Iterator {

        private final int[] max;
        private final int[] counters;

        public WorldIterator(Coords3D worldsize) {
            this.max = new int[]{(int) worldsize.getX(), (int) worldsize.getY(), (int) worldsize.getZ()};
            this.counters = new int[]{0, 0, 0};
        }

        @Override
        public boolean hasNext() {
            for (int i = 0; i < 3; i++) {
                if (this.counters != max) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Coords3D next() {

            for (int i = 0; i < 3; i++) {
                if (counters[i] < max[i]) {
                    counters[i]++;
                    break;
                } else {
                    counters[i] = 0;
                }
            }

            return new Coords3D(counters[0], counters[1], counters[2]);
        }
    }
}
