package gengine.rendering;

import gengine.world.World;
import gengine.world.entity.WorldEntity;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Generic interface to be used by all WorldRenderers - things that render the
 * World to be displayed.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface WorldRenderer {

    /**
     * Render a given world into the supplied BufferedImage
     *
     * @param world World to render
     * @param bi    BufferedImage to render into
     * @param ro    WorldRendererOptions, carrying options like focus point,
     *              offsets, zoom and stuff like that
     *
     * @throws RendererException
     */
    public void render(World world, BufferedImage bi, WorldRendererOptions ro) throws RendererException;

    /**
     * Returns an array of WorldEntities found on the supplied mouse position on
     * the BufferedImage last rendered onto. The WorldEntities should be sorted
     * by the Z-axis - the closest (topmost) WorldEntity should have the lowest
     * index.
     *
     * @param mousepos mouse position on the last rendered BufferedImage
     *
     * @return array of WorldEntities intersecting that point
     */
    public WorldEntity[] getEntitiesOnPosition(Point mousepos);
}
