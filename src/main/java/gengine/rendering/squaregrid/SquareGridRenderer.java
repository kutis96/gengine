package gengine.rendering.squaregrid;

import gengine.rendering.*;
import gengine.rendering.WorldRendererOptions.Flags;
import gengine.util.coords.Coords3D;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.Tile;
import gengine.world.tile.TilesetIndexException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class for rendering of square/rectangular grid worlds.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SquareGridRenderer implements WorldRenderer {

    private static final Logger LOG = Logger.getLogger(SquareGridRenderer.class.getName());

    private final int tilewidth;
    private final int tileheight;

    public SquareGridRenderer() {
        this(60, 60);
    }

    public SquareGridRenderer(int tilesize) {
        this(tilesize, tilesize);
    }

    public SquareGridRenderer(int tilewidth, int tileheight) {
        this.tileheight = tileheight;
        this.tilewidth = tilewidth;
    }

    @Override
    public void render(World world, BufferedImage surface, WorldRendererOptions wropt) throws RendererException {
        if (world instanceof TiledWorld) {
            this.render((TiledWorld) world, surface, wropt);
        } else {
            throw new RendererException("Invalid World Type Detected");
        }
    }

    private void render(TiledWorld tw, BufferedImage surface, WorldRendererOptions wrop) throws RendererException {
        //  TODO: Further code cleanup
        //      - probably for this thing off to a completely different class
        //        and parametrize everything, also use some functions for coordinate conversion
        //        and aaaaah!        
        
        //scaled pixel Height and Width of the tiles
        int scaledHeight = (int) (this.tileheight * wrop.getZoom());
        int scaledWidth = (int) (this.tilewidth * wrop.getZoom());

        //pixel offsets of the visible area
        int xoff = (int) (wrop.getCameraPosition().getX() * scaledWidth
                + wrop.getCameraOffset().getX());
        int yoff = (int) (wrop.getCameraPosition().getY() * scaledHeight
                + wrop.getCameraOffset().getY());

        //WORLD (tile) coordinates specifying the drawn boundaries
        Coords3D upperVisibleBound;
        Coords3D lowerVisibleBound;

        //CONVERT PIXEL BOUNDARIES TO WORLD BOUNDARIES 
        {
            lowerVisibleBound = new Coords3D(
                    xoff / scaledWidth,
                    0,
                    yoff / scaledHeight
            );

            upperVisibleBound = new Coords3D(
                    (surface.getWidth() + xoff) / scaledWidth,
                    0,
                    (surface.getHeight() + yoff) / scaledHeight
            );

        }   //END OF PIXEL TO WORLD BOUNDARY CONVERSION

        Graphics2D g2d = surface.createGraphics();
        surface.setAccelerationPriority(1);

        {   //TILE RENDERING
            for (int x = (int) lowerVisibleBound.getX(); x < upperVisibleBound.getX(); x++) {
                for (int y = (int) lowerVisibleBound.getY(); y < upperVisibleBound.getY(); y++) {
                    try {
                        Tile t = tw.getWorldtile(new Coords3D(x, y, 0));
                        Image i = t.render();

                        if (i == null) {
                            //tiles that don't render should be simply skipped
                            //  - this is possibly asking for a major clusterfudge sooner or later

                            continue;
                        }

                        g2d.drawImage(
                                i,
                                x * scaledWidth + xoff, //xpos
                                y * scaledHeight + yoff, //ypos
                                scaledWidth, //width
                                scaledHeight, //height
                                null);

                    } catch (TilesetIndexException ex) {
                        String message = "Found a tile with an invalid index while rendering!\n\t" + ex.getMessage();

                        LOG.log(Level.WARNING, message);

                        if (wrop.hasFlag(Flags.DIE_ON_MISSING_TILES)) {
                            throw (RendererException) (Exception) ex;
                        }
                    }
                }
            }
        }   //END OF TILE RENDERING

        { //ENTITY RENDERING
            
            List<WorldEntity> renderedEntities = new ArrayList<>();

            //Fill the renderedEntities list with entities that are visible
            for (WorldEntity we : tw.getEntities()) {
                if (SquareGridUtils.isWithinIgnoringZ(
                        (Coords3D) we.getPos(),
                        lowerVisibleBound,
                        upperVisibleBound)) {
                    renderedEntities.add(we);
                }
            }

            //sort entities by Z (depth) and then by Y (vertical axis) for rendering
            Collections.sort(renderedEntities, new SquareGridUtils.EntityZYComparator());

            for (WorldEntity we : renderedEntities) {

                we.render();

                Image i = we.render();

                Coords3D pos = (Coords3D) we.getPos();

                //draw the entity as it's already sorted and thus positioned correctly
                // assuming the for loop is indeed sequential, which it better should be
                g2d.drawImage(
                        i,
                        (int) (pos.getX() * scaledWidth + xoff - i.getWidth(null) / 2),
                        (int) (pos.getY() * scaledHeight + yoff - i.getHeight(null) / 2),
                        (int) (i.getWidth(null) * wrop.getZoom()),
                        (int) (i.getHeight(null) * wrop.getZoom()),
                        null);
            }
            
        }   // END OF ENTITY RENDERING
    }
}
