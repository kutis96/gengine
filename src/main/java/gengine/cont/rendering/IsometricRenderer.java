package gengine.cont.rendering;

import gengine.tile.Tile;
import gengine.tile.TiledWorld;
import java.awt.*;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class IsometricRenderer {

    private final int tilewidth;

    public IsometricRenderer() {
        this.tilewidth = 120;
    }

    public IsometricRenderer(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    public void render(TiledWorld iw, Graphics g, RendererOptions ro) {

        //TODO: add entity rendering
        //TODO: add clickmap rendering (opt?)
        //TODO: refactor
        //TODO: modify scaling, so it actually scales the whole image correctly
        //TODO: modify the rendering so it actually renders by rows (back to front) instead of the cartesian sweep
        //TODO: render into BufferedImage instead?
        
        //TODO: replace screen blanking with something less silly
        g.fillRect(0, 0, 65535, 65535);
        
        Tile[][][] tiles = iw.getWorldtiles();

        int max = Math.max(iw.getWorldSize().getX(), iw.getWorldSize().getY());
        
        int tilesize = (int) (120 * ro.zoom);

        int th = tilesize / 2;
        int tq = tilesize / 4;

        for (int x = 0; x < iw.getWorldSize().getX(); x++) {
            for (int y = 0; y < iw.getWorldSize().getY(); y++) {
                for (int z = 0; z < iw.getWorldSize().getZ(); z++) {
                    Tile t = tiles[x][y][z];

                    if (t != null && t.isVisible()) {

                        Image img = t.render();

                        //Image scaling:
                        
                        g.drawImage(
                                t.render(),
                                ro.cameraOffset.getX() + th * x - th * y,
                                ro.cameraOffset.getY() + th + tq * y + tq * x - (th + tq) * z - img.getHeight(null),
                                tilesize,
                                img.getHeight(null),
                                null);
                    }
                }
            }
        }
    }
}
