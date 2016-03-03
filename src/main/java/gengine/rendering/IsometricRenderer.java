package gengine.rendering;

import gengine.tile.Tile;
import gengine.tile.TiledWorld;
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

        //TODO: add entity rendering -- after I add the actual entities first
        //TODO: add clickmap rendering (opt?) -- to be done waaay later on
        //TODO: refactor - everyone loves refactoring!
        //TODO: modify the rendering so it actually renders by rows (back to front) instead of the ;cartesian sweep'
        //TODO: draw only the stuff that's actually visible (in the given window)
        //      ^ can be solved with a tiny bit of math.
        //TODO: end up using something a bit more universal at some point
        //      ^ soon.
        //
        // WIP:
        //----------------------------------------------------------------------
        //
        // DONE:
        //----------------------------------------------------------------------
        // - render into BufferedImage instead
        // - replace screen blanking with something less silly
        // - RO nullchecking
        // - modify scaling, so it actually scales the whole texture correctly
        
        if (ro == null) {
            ro = new RendererOptions();
        }

        Graphics2D g = bi.createGraphics();
        
        g.setBackground(Color.BLACK);
        
        Tile[][][] tiles = wrld.getWorldtiles();

        int tilesize = (int) (this.tilewidth * ro.zoom);

        int th = tilesize / 2;
        int tq = tilesize / 4;

        for (int x = 0; x < wrld.getWorldSize().getX(); x++) {
            for (int y = 0; y < wrld.getWorldSize().getY(); y++) {
                for (int z = 0; z < wrld.getWorldSize().getZ(); z++) {
                    Tile current_tile = tiles[x][y][z];

                    if (current_tile != null && current_tile.isVisible()) {

                        Image rendered_tile = current_tile.render();

                        double rescratio = tilesize / (double) rendered_tile.getWidth(null);   //rescaling ratio

                        //draw image on the rendered surface
                        g.drawImage(rendered_tile,
                                ro.cameraOffset.getX() + th * x - th * y,
                                ro.cameraOffset.getY() + th + tq * y + tq * x - (th + tq) * z - (int) (rendered_tile.getHeight(null) * rescratio),
                                tilesize,
                                (int) (rendered_tile.getHeight(null) * rescratio),
                                null);
                    }
                }
            }
        }

        g.dispose();
    }
}
