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

        //TODO: add entity rendering -- after I add the actual entities first
        //TODO: add clickmap rendering (opt?) -- to be done waaay later on
        //TODO: modify the rendering so it actually renders by rows (back to front) instead of the 'cartesian sweep'
        //TODO: draw only the stuff that's actually visible (in the given window)
        //      ^ can be solved with a tiny bit of math.
        //TODO: end up using something a bit more universal at some point
        //      ^ soon.
        //TODO: visibility prediction, so one doesn't need to render the whole world all the time, but only the visible portion
        //
        // WIP:
        //----------------------------------------------------------------------
        // refactor - everyone loves refactoring!
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

        int tilesize = (int) (this.tilewidth * ro.zoom);

        int gx = (int) wrld.getWorldSize().getX();
        int gy = (int) wrld.getWorldSize().getY();

        for (int x = 0; x < wrld.getWorldSize().getX(); x++) {
            for (int y = 0; y < wrld.getWorldSize().getY(); y++) {
                for (int z = 0; z < wrld.getWorldSize().getZ(); z++) {

                    Coords3D isomcoord = new Coords3D(x, y, z);

                    Tile current_tile = wrld.getWorldtile(isomcoord);

                    if (current_tile != null) {

                        Image rendered_tile = current_tile.render();

                        double rescratio = tilesize / (double) rendered_tile.getWidth(null);

                        Coords2D conv = IsometricUtils.convIsom2Graph(
                                isomcoord,
                                ro.cameraOffset,
                                ro.cameraPosition,
                                tilesize,
                                3 * tilesize / 4);

                        //draw image on the rendered surface
                        g.drawImage(rendered_tile,
                                (int) conv.getX(),
                                (int) (conv.getY() - rendered_tile.getHeight(null) * rescratio),
                                tilesize,
                                (int) (rendered_tile.getHeight(null) * rescratio),
                                null);
                    }
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
