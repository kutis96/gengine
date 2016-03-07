package gengine.rendering.isometric;

import gengine.rendering.RendererOptions;
import gengine.util.coords.Coords2D;
import gengine.util.coords.Coords3D;
import gengine.world.tile.Tile;
import gengine.world.TiledWorld;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class IsometricRenderer {

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
     * Renders the TiledWorld into a specified Graphics objects with given
     * RendererOptions. This functions is to be heavily remade.
     *
     * @param wrld TiledWorld to render.
     * @param bi   BufferedImage object to render onto.
     * @param ro   RendererOptions supplying all the renderer options!
     */
    public void render(TiledWorld wrld, BufferedImage bi, RendererOptions ro) {

        //TODO:
        //----------------------------------------------------------------------
        // - add clickmap rendering (opt?) -- to be done waaay later on
        //     ^ can be solved with a tiny bit of math.
        // - visibility prediction, so one doesn't need to render the whole world all the time, but only the visible portion
        // - end up using something a bit more universal at some point
        //     ^ soon.
        //
        // WIP:
        //----------------------------------------------------------------------
        // - add entity rendering -- after I add the actual entities first
        // - refactor - everyone loves refactoring!
        //
        // DONE:
        //----------------------------------------------------------------------
        // - modify the rendering so it actually renders by rows (back to front) instead of the 'XY sweep'
        //
        
        if (ro == null) {
            ro = new RendererOptions();
        }

        Graphics2D g = bi.createGraphics();

        g.setBackground(Color.BLACK);   //blanking

        //Size of tiles in pixels, scaled by zoom
        int tilesize = (int) (this.tilewidth * ro.zoom);

        //Gets the current worldsize
        Coords3D wrldsize = wrld.getWorldSize();
        
        //Width and height of the rendered area, in tiles
        //  this shall be changed accordingly later, based on the tile size and rendered area size
        int drawheight = (int) wrldsize.getY();
        int drawwidth = (int) wrldsize.getX();
        
        //Loops!
        for (int r = 0; r < (2 * Math.max(drawheight, drawwidth) - Math.abs(drawheight - drawwidth)) - 1; r++){
            for (int h = Math.max(0, r - drawwidth + 1); h <= Math.min(r, drawheight - 1); h++) {
                
                //Actual X and Y positions of the tiles (in tile coords)
                //  this shall be offset later, based on the camera position, tile size, and rendered area size
                int x = r - h;
                int y = h;                
                
                //Z sweep
                for (int z = 0; z < wrldsize.getZ(); z++) {
                    
                    //ACTUAL DRAWING GOES HERE
                    
                    Coords3D coords = new Coords3D(x, y, z);

                    Tile current_tile = wrld.getWorldtile(coords);

                    if (current_tile != null) {

                        Image rendered_tile = current_tile.render();

                        //Scaling ratio of the tiles.
                        //  calculated on the horizontal axis
                        double scaling = tilesize / (double) rendered_tile.getWidth(null);

                        //Converts isometric coordinates into graphic coordinates (aka pixel coordinates on the screen
                        Coords2D conv = IsometricUtils.convIsom2Graph(
                                coords,
                                ro.cameraOffset,
                                ro.cameraPosition,
                                tilesize,
                                3 * tilesize / 4);

                        //draw image on the rendered surface
                        g.drawImage(
                                //Image to draw
                                rendered_tile,
                                
                                //position gotten from the converter
                                (int) conv.getX(),
                                (int) (conv.getY() - rendered_tile.getHeight(null) * scaling),
                                
                                tilesize,                                           //Width
                                (int) (rendered_tile.getHeight(null) * scaling),    //Height
                                null    //Observer
                        );
                    }
       
                    //ACTUAL DRAWING ENDS HERE
                }
            }
        }

        Coords2D camaimpos = IsometricUtils.convIsom2Graph(
                new Coords3D(0, 0, 0),
                ro.cameraOffset,
                ro.cameraPosition,
                tilesize,
                3 * tilesize / 4);

        g.drawRect((int) camaimpos.getX(), (int) camaimpos.getY(), -tilesize, -tilesize / 2);

        g.dispose();
    }
}
