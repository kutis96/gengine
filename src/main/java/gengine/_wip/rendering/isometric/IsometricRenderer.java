package gengine._wip.rendering.isometric;

import gengine.rendering.*;
import gengine.util.coords.Coords2D;
import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.tile.TilesetIndexException;
import java.awt.*;
import java.awt.image.BufferedImage;
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
     * @throws WorldTypeMismatchException Thrown when the supplied World happens
     *                                    to be something other than TileWorld
     */
    @Override
    public void render(World world, BufferedImage bi, WorldRendererOptions ro) throws WorldTypeMismatchException, RendererException {
        if (world instanceof TiledWorld) {
            this.render((TiledWorld) world, bi, ro);
        } else {
            throw new WorldTypeMismatchException();
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
        int tilesize = (int) (this.tilewidth * wrop.zoom);

        //Gets the current worldsize
        Coords3D wrldsize = world.getWorldSize();

        //Width and height of the rendered area, in tiles
        //  this shall be changed accordingly later, based on the tile size and rendered area size
        int drawheight = (int) wrldsize.getY();
        int drawwidth = (int) wrldsize.getX();

//        //Loops orignally used for row scanning, which turned out to be a facepalming failure.
//          I'll keep this snipped commented out in here anyway, it could come in handy at some point.
//        for (int r = 0; r < (2 * Math.max(drawheight, drawwidth) - Math.abs(drawheight - drawwidth)) - 1; r++){
//            for (int h = Math.max(0, r - drawwidth + 1); h <= Math.min(r, drawheight - 1); h++) {
//                
//                //Actual X and Y positions of the tiles (in tile coords)
//                //  this shall be offset later, based on the camera position, tile size, and rendered area size
//                int x = r - h;
//                int y = h;                
//                
        for (int x = 0; x < world.getWorldSize().getX(); x++) {
            for (int y = 0; y < world.getWorldSize().getY(); y++) {
                //Z sweep
                for (int z = 0; z < wrldsize.getZ(); z++) {

                    //ACTUAL DRAWING GOES HERE
                    Coords3D coords = new Coords3D(x, y, z);

                    try {
                        Tile current_tile = world.getWorldtile(coords);

                        if (current_tile != null) {

                            Image rendered_tile = current_tile.render();
                            
                            if (rendered_tile == null){
                                continue;
                            }

                            //Scaling ratio of the tiles.
                            //  calculated on the horizontal axis
                            double scaling = tilesize / (double) rendered_tile.getWidth(null);

                            //Converts isometric coordinates into graphic coordinates (aka pixel coordinates on the screen
                            Coords2D conv = IsometricUtils.convIsom2Graph(
                                    coords,
                                    wrop.cameraOffset,
                                    wrop.cameraPosition,
                                    tilesize,
                                    3 * tilesize / 4);

                            //draw image on the rendered surface
                            g.drawImage(
                                    //Image to draw
                                    rendered_tile,
                                    //position gotten from the converter
                                    (int) conv.getX(),
                                    (int) (conv.getY() - rendered_tile.getHeight(null) * scaling),
                                    tilesize, //Width
                                    (int) (rendered_tile.getHeight(null) * scaling), //Height
                                    null //Observer
                            );
                        }
                    } catch (TilesetIndexException ex) {
                        String message = "Found a tile with an invalid index while rendering!\n\t" + ex.getMessage();
                        
                        LOG.log(Level.WARNING, message);
                        
                        if((wrop.flags & WorldRendererOptions.RenderFlags.DIE_ON_MISSING_TILES.getFlagValue()) != 0){
                            throw new RendererException(message);
                        }
                    }

                    //ACTUAL DRAWING ENDS HERE
                }
            }
        }

        Coords2D camaimpos = IsometricUtils.convIsom2Graph(
                new Coords3D(0, 0, 0),
                wrop.cameraOffset,
                wrop.cameraPosition,
                tilesize,
                3 * tilesize / 4);

        g.drawRect((int) camaimpos.getX(), (int) camaimpos.getY(), -tilesize, -tilesize / 2);

        g.dispose();
    }
}
