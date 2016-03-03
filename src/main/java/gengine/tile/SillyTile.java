package gengine.tile;

import gengine.util.coords.Coords;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A completely silly tile used for minor debugging and nothing much else.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SillyTile extends Tile {

    final Color col;

    /**
     * Constructs the SillyTile with a completely random color.
     * @param pos
     */
    public SillyTile(Coords pos) {
        super(pos);
        this.col = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    /**
     * Constructs the SillyTile with a specified color.
     *
     * @param col The color of this SillyTile.
     */
    public SillyTile(Coords pos, Color col) {
        super(pos);
        this.col = col;
    }

    /**
     * Does nothing whatsoever, SillyTile is completely static. (as in it
     * doesn't animate or react to anything).
     *
     * @param iw See Tile documentation.
     * @param dt See Tile documentation.
     */
    @Override
    public void tick(TiledWorld iw, long dt) {
        //do nothing whatsoever
    }

    /**
     * Renders a rhombic tile.
     * @return 
     */
    @Override
    public Image render() {
        
        int tilesize = 120;
        int th = tilesize / 2;

        BufferedImage bi = new BufferedImage(tilesize, th, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) bi.getGraphics();

        int w = bi.getWidth();
        int wh = w / 2;
        int h = bi.getHeight();
        int hh = h / 2;

        g2d.setColor(col);

        g2d.drawLine(wh, 0, 0, hh);
        g2d.drawLine(wh, h, 0, hh);
        g2d.drawLine(wh, 0, w, hh);
        g2d.drawLine(wh, h, w, hh);
        
        return bi;
    }

}
