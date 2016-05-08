package gengine.rendering.text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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
public class TextRenderer {

    private static final Logger LOG = Logger.getLogger(TextRenderer.class.getName());

    private final int hpix, vpix;
    private final int hspace, vspace;
    private final BufferedImage fontImage;

    public TextRenderer() throws IOException {
        try {
            this.hpix = 8;
            this.vpix = 16;
            this.hspace = 1;
            this.vspace = 0;
            URL url = TextRenderer.class.getResource("/font_shaded.png");
            LOG.log(Level.INFO, "Loading font resource {0}", url);
            this.fontImage = ImageIO.read(url);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Failed to load font resource", ex);
            throw ex;
        }
    }

    public TextRenderer(int xpix, int ypix, int hspace, int vspace, BufferedImage fontImage) {
        this.hpix = xpix;
        this.vpix = ypix;
        this.hspace = hspace;
        this.vspace = vspace;
        this.fontImage = fontImage;
    }

    public Image drawCharacter(char c) {
        int nX = fontImage.getWidth(null) / hpix;
        int nY = fontImage.getHeight(null) / vpix;

        int x = c % nX;
        int y = c / nX;

        if (c >= nX * nY) {
            return null;
        }

        return this.fontImage.getSubimage(x * hpix, y * vpix, hpix, vpix);
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
        }

        BufferedImage rendered = new BufferedImage(
                maxcols * hpix + Math.max(1, (maxcols - 1) * hspace),
                rows * vpix + Math.max(1, (rows - 1)) * vspace,
                BufferedImage.TYPE_4BYTE_ABGR);

        Graphics g = rendered.getGraphics();

        LOG.log(Level.FINE, "The given text contains {0} rows and {1} cols", new Object[]{rows, maxcols});

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
                    g.drawImage(this.drawCharacter(ch),
                            c * (hpix + hspace),
                            r * (vpix + vspace), null);
                    c++;
                    break;
                }
            }
        }
        
        return rendered;
    }

    public int getCharWidth() {
        return hpix;
    }

    public int getCharHeight() {
        return vpix;
    }
    
    public int getHSpacing(){
        return hspace;
    }
    
    public int getVSpacing(){
        return vspace;
    }
}
