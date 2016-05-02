package gengine.rendering.squaregrid;

import gengine.rendering.*;
import gengine.rendering.WorldRendererOptions.Flags;
import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.Tile;
import gengine.world.tile.TilesetIndexException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A renderer for square/rectangular grid worlds. This was the easiest one to
 * implement.
 *
 * This class also checks for mouse hits and is responsible for returning the
 * world's entities found under one's mouse pointer or something like that. It
 * seemed like a good idea, since only the renderer really knows where on screen
 * those actually happen to be.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SquareGridRenderer implements WorldRenderer {

    private static final Logger LOG = Logger.getLogger(SquareGridRenderer.class.getName());

    private final int tilewidth;
    private final int tileheight;

    private final List<WorldEntity> lastRenderedEntities;
    private final Point lastPixelOffset;
    private final float[] lastScale = {1, 1};
    private final Long lock;

    /**
     * Constructs this SquareGridRenderer to use some generic tile size.
     * Defaults to 64x64px square tiles.
     */
    public SquareGridRenderer() {
        this(64, 64);
    }

    /**
     * Construct the SquareGridRenderer to use square tiles of the specified
     * dimensions in pixels.
     *
     * @param tilesize
     */
    public SquareGridRenderer(int tilesize) {
        this(tilesize, tilesize);
    }

    /**
     * Construct the SquareGridRenderer to use the specified tile dimensions in
     * pixels as the base.
     *
     * @param tilewidth
     * @param tileheight
     */
    public SquareGridRenderer(int tilewidth, int tileheight) {
        this.tileheight = tileheight;
        this.tilewidth = tilewidth;
        this.lastRenderedEntities = new ArrayList<>();
        this.lastPixelOffset = new Point();
        this.lock = 42L;
    }

    @Override
    public void render(World world, BufferedImage surface, WorldRendererOptions wropt) throws RendererException {
        if (world instanceof TiledWorld) {
            try {
                this.render((TiledWorld) world, surface, wropt);
            } catch (ValueException ex) {
                String msg = "Found a ValueException inside the renderer somewhere.";
                LOG.log(Level.SEVERE, msg, ex);
                throw new RendererException(msg);
            }
        } else {
            LOG.severe("Invalid World Type detected!");
            throw new RendererException("Invalid World Type detected");
        }
    }

    private void render(TiledWorld world, BufferedImage surface, WorldRendererOptions wrop) throws RendererException, ValueException {
        //  TODO: Further code cleanup
        //      - probably for this thing off to a completely different class
        //        and parametrize everything, also use some functions for coordinate conversion
        //        and aaaaah!        

        //scaled pixel Height and Width of the tiles
        int scaledHeight = (int) (this.tileheight * wrop.getZoom());
        int scaledWidth = (int) (this.tilewidth * wrop.getZoom());  

        //pixel offsets of the visible area
        int xoff = (int) (wrop.getCameraPosition().getX() * scaledWidth
                + wrop.getCameraOffset().getX() + this.tilewidth / 2);
        int yoff = (int) (wrop.getCameraPosition().getY() * scaledHeight
                + wrop.getCameraOffset().getY() + this.tileheight / 2);

        //WORLD (tile) coordinates specifying the drawn boundaries
        Coords3D upperVisibleBound;
        Coords3D lowerVisibleBound;

        //CONVERT PIXEL BOUNDARIES TO WORLD BOUNDARIES 
        {
            //this one seems to be completely wrong.
            lowerVisibleBound = new Coords3D(
                    Math.max(0, (float) Math.floor(xoff / scaledWidth) - 1),
                    Math.max(0, (float) Math.floor(yoff / scaledHeight) - 1),
                    0
            );

            upperVisibleBound = new Coords3D(
                    Math.min(world.getWorldSize().getX(), (surface.getWidth() + xoff) / scaledWidth),
                    Math.min(world.getWorldSize().getY(), (surface.getHeight() + yoff) / scaledHeight),
                    0
            );

        }   //END OF PIXEL TO WORLD BOUNDARY CONVERSION

        Graphics2D g2d = surface.createGraphics();
        surface.setAccelerationPriority(1);

        synchronized (world) {   //TILE RENDERING

            for (int x = (int) lowerVisibleBound.getX(); x < upperVisibleBound.getX(); x++) {
                for (int y = (int) lowerVisibleBound.getY(); y < upperVisibleBound.getY(); y++) {
                    try {
                        Tile t = world.getWorldtile(new Coords3D(x, y, 0));
                        Image i = t.render();

                        if (i == null) {
                            //tiles that don't render should be simply skipped
                            //  - this is possibly asking for a major clusterfudge sooner or later
                            continue;
                        }

                        g2d.drawImage(
                                i,
                                (x * scaledWidth) + xoff, //xpos
                                (y * scaledHeight) + yoff, //ypos
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

            ArrayList<WorldEntity> renderedEntities = new ArrayList<>();

            synchronized (world) {

                //Fill the renderedEntities list with entities that are visible
                for (WorldEntity we : world.getEntities()) {
                    if (SquareGridUtils.isWithinIgnoringZ(
                            we.getPos(),
                            lowerVisibleBound,
                            upperVisibleBound)) {
                        renderedEntities.add(we);
                    }
                }

            }

            //sort entities by Z (depth) and then by Y (vertical axis) for rendering
            //Collections.sort(renderedEntities, new SquareGridUtils.EntityZYComparator());
            for (WorldEntity we : renderedEntities) {

                we.render();

                Image i = we.render();

                if (i == null) {
                    LOG.log(Level.WARNING, "Missing entity rendering ({0})", we.toString());
                    continue;
                }

                Coords3D pos = we.getPos();

                //draw the entities as the renderedEntities list is already sorted
                // and thus everything is positioned correctly
                // assuming the for loop is indeed sequential, which it better should be
                //TODO: check whether the scaling is actually correct, it seems somewhat fishy
                g2d.drawImage(
                        i,
                        (int) (pos.getX() * scaledWidth + xoff - i.getWidth(null) / 2),
                        (int) (pos.getY() * scaledHeight + yoff - i.getHeight(null) / 2),
                        (int) (i.getWidth(null) * wrop.getZoom()),
                        (int) (i.getHeight(null) * wrop.getZoom()),
                        null);
            }

            synchronized (lock) {
                lastRenderedEntities.clear();
                lastRenderedEntities.addAll(renderedEntities);

                lastPixelOffset.setLocation(xoff, yoff);

                lastScale[0] = (float) wrop.getZoom();
                lastScale[1] = (float) wrop.getZoom();
            }

        }   // END OF ENTITY RENDERING
    }

    @Override
    public WorldEntity[] getEntitiesOnPosition(Point mousepos) {

        List<WorldEntity> ents;
        ents = new LinkedList<>();

        synchronized (lock) {

            for (WorldEntity we : lastRenderedEntities) {

                Point p = entityPxCoords(lastPixelOffset, lastScale, we.getPos());
                p.translate(-mousepos.x, -mousepos.y);

                if (we.mouseHit(p)) {
                    ents.add(we);
                }
            }
        }

        return ents.toArray(new WorldEntity[ents.size()]);
    }

    private Point entityPxCoords(Point pixelOffset, float[] scale, Coords3D entityCoordinates) {
        int x = (int) (tilewidth * entityCoordinates.getX() * scale[0] + pixelOffset.x);
        int y = (int) (tileheight * entityCoordinates.getY() * scale[1] + pixelOffset.y);

        return new Point(x, y);
    }
}
