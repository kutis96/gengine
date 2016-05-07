package gengine.rendering.squaregrid;

import gengine.rendering.RenderableContainer;
import gengine.rendering.*;
import gengine.rendering.WorldRendererOptions.Flags;
import gengine.rendering.overlay.HasOverlays;
import gengine.rendering.overlay.Overlay;
import gengine.util.coords.*;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.Tile;
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

    private final Object lock = new Object();
    private List<WorldEntity> lastRenderedEntities;
    private Point lastPixelOffset;
    private final float[] lastScale = {1, 1};

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

        //  TODO: render tiles while rendering entities as well, to allow for tile overlays etc.
        //scaled pixel Height and Width of the tiles
        int scaledHeight = (int) (this.tileheight * wrop.getZoom());
        int scaledWidth = (int) (this.tilewidth * wrop.getZoom());

        //pixel offsets of the visible area
        int xoff = (int) (-wrop.getCameraPosition().getX() * scaledWidth
                + wrop.getCameraOffset().getX() + this.tilewidth / 2 + surface.getWidth() / 2);
        int yoff = (int) (-wrop.getCameraPosition().getY() * scaledHeight
                + wrop.getCameraOffset().getY() + this.tileheight / 2 + surface.getHeight() / 2);

        //WORLD (tile) coordinates specifying the drawn boundaries
        Coords3D upperVisibleBound;
        Coords3D lowerVisibleBound;

        //CONVERT PIXEL BOUNDARIES TO WORLD BOUNDARIES
//        lowerVisibleBound = new Coords3D(
//                Math.max(0, (float) Math.floor((double) xoff / scaledWidth) - 1),
//                Math.max(0, (float) Math.floor((double) yoff / scaledHeight) - 1),
//                0
//        );
//
//        upperVisibleBound = new Coords3D(
//                Math.min(world.getWorldSize().getX(), (surface.getWidth() + xoff) / scaledWidth),
//                Math.min(world.getWorldSize().getY(), (surface.getHeight() + yoff) / scaledHeight),
//                0
//        );
        lowerVisibleBound = new Coords3D();
        upperVisibleBound = world.getWorldSize();
        //END OF PIXEL TO WORLD BOUNDARY CONVERSION

        Graphics2D g2d = surface.createGraphics();
        surface.setAccelerationPriority(1);

        ArrayList<RenderableContainer> renderedThings = new ArrayList<>((int) world.getTileAmount());
        ArrayList<WorldEntity> renderedEntities = new ArrayList<>();    //I do this separately as Entities are clickable, unlike tiles
        //and I actually reuse this later.
        //TODO: clean that up

        synchronized (lock) {
            
            //Add all tiles to the rendered things
            for (int x = (int) lowerVisibleBound.getX(); x < upperVisibleBound.getX(); x++) {
                for (int y = (int) lowerVisibleBound.getY(); y < upperVisibleBound.getY(); y++) {
                    Tile t = world.getWorldtile(new Coords3D(x, y, 0));
                    if (t != null) {
                        renderedThings.add(new RenderableContainer(new Coords3D(x, y, -1), t));
                    }
                }
            }

            //Add all WorldEntities to the rendered things
            for (WorldEntity we : world.getEntities()) {
                if (SquareGridUtils.isWithinIgnoringZ(
                        we.getPos(),
                        lowerVisibleBound,
                        upperVisibleBound)) {

                    renderedEntities.add(we);   //used for mouse click detectiony stuff

                    renderedThings.add(new RenderableContainer(we.getPos(), we));

                    //Render overlays as well
                    if (we instanceof HasOverlays) {
                        HasOverlays ho = (HasOverlays) we;
                        for (Overlay o : ho.getOverlays()) {
                            try {
                                if (o != null && o.getOffset() != null) {
                                    renderedThings.add(new RenderableContainer(new Coords3D(we.getPos().add(o.getOffset())), o));
                                }
                            } catch (DimMismatchException ex) {
                                LOG.log(Level.SEVERE, "Unexpected DimMismatch >:C", ex);
                            }
                        }
                    }

                }
            }

        }

        //sort renderables by Z (depth) and then by Y (vertical axis) for rendering
        Collections.sort(renderedThings, new SquareGridUtils.RContainerZYComparator());

        for (RenderableContainer rc : renderedThings) {

            Image i = rc.ren.render();

            if (i == null) {
                LOG.log(Level.WARNING, "Unable to render ({0})", rc.toString());
                continue;
            }

            Coords3D pos = rc.pos;

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

            if (wrop.hasFlag(Flags.DEBUGMODE)) {
                //draw lines between entities or something here
            }
        }

        synchronized (lock) {
            //This bit is there to set some variables used by the mouse pos detection etc.
            lastRenderedEntities.clear();
            lastRenderedEntities.addAll(renderedEntities);

            lastPixelOffset.setLocation(xoff, yoff);

            lastScale[0] = (float) wrop.getZoom();
            lastScale[1] = (float) wrop.getZoom();
        }
    }

    @Override
    public WorldEntity[] getEntitiesOnPosition(Point mousepos) {

        //TODO: to be replaced with a newer version taking in account tile visibilities etc?
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
