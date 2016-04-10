package gengine.rendering;

import gengine.util.coords.Coords3D;
import gengine.world.World;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface WorldRenderer {

    /**
     * Renders the given World into the given image.
     *
     * @param world World to render
     * @param bi    Image to render into
     * @param ro    Renderer options
     *
     * @throws WorldTypeMismatchException
     * @throws RendererException
     */
    public void render(World world, BufferedImage bi, WorldRendererOptions ro) throws WorldTypeMismatchException, RendererException;
    
    public Coords3D getWorldCoords(World world, Point point)  throws WorldTypeMismatchException, RendererException;
}
