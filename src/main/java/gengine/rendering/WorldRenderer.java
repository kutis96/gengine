package gengine.rendering;

import gengine.world.World;
import java.awt.image.BufferedImage;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface WorldRenderer {
    public void render(World world, BufferedImage bi, WorldRendererOptions ro) throws WorldTypeMismatchException, RendererException;
}
