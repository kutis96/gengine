package gengine.rendering.overlay.text;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A class drawing a monospace font.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class FontDrawer {

    private static final Logger LOG = Logger.getLogger(FontDrawer.class.getName());

    private final int xpix, ypix;
    private final BufferedImage fontImage;

    public FontDrawer() throws IOException {
        try {
            this.xpix = 8;
            this.ypix = 16;
            URL url = FontDrawer.class.getResource("/font.png");
            LOG.log(Level.INFO, "Loading font resource {0}", url);
            this.fontImage = ImageIO.read(url);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Failed to load font resource", ex);
            throw ex;
        }
    }

    public Image getCharacter(char c) {
        int nX = fontImage.getWidth(null) / xpix;
        int nY = fontImage.getHeight(null) / ypix;

        int x = c % nX;
        int y = c / nX;

        if (c >= nX * nY) {
            return null;
        }

        return this.fontImage.getSubimage(x * xpix, y * ypix, xpix, ypix);
    }

    public Image drawString(String s) {

        int cols = 0;
        int maxcols = 0;
        int rows = 1;

        //assess image size
        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '\n': {
                    rows++;
                    cols = 0;
                    break;
                }
                default: {
                    cols++;
                    maxcols = Math.max(maxcols, cols);
                    break;
                }
            }
            System.out.println("r:" + rows + " c:" + cols + " mc:" + maxcols);
        }

        BufferedImage ret = new BufferedImage(maxcols * xpix, rows * ypix, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = ret.getGraphics();

        LOG.log(Level.INFO, "The given text contains {0} rows and {1} cols", new Object[]{rows, maxcols});

        //actual drawing
        int r = 0;
        int c = 0;
        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '\n': {
                    r++;
                    c = 0;
                    break;
                }
                default: {
                    LOG.log(Level.INFO, "{0} {1},{2}", new Object[]{ch, c, r});
                    g.drawImage(this.getCharacter(ch), c * xpix, r * ypix, null);
                    c++;
                    break;
                }
            }
        }

        return ret;
    }

    public int getCharWidth() {
        return xpix;
    }

    public int getCharHeight() {
        return ypix;
    }
}
