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

    public SquareGridRenderer(int tw, int th) {
        this.tileheight = th;
        this.tilewidth = tw;
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
        int drawH = (int) (this.tileheight * wrop.getZoom());
        int drawW = (int) (this.tilewidth * wrop.getZoom());

        int maxX = (int) tw.getWorldSize().getX();
        int maxY = (int) tw.getWorldSize().getY();
        
        float xscale = (float) this.tileheight/drawH;
        float yscale = (float) this.tileheight/drawH;

        int xoff = (int) (wrop.getCameraPosition().getX() * drawW + wrop.getCameraOffset().getX() + (surface.getWidth() - maxX * this.tilewidth) / 2);
        int yoff = (int) (wrop.getCameraPosition().getY() * drawH + wrop.getCameraOffset().getY() + (surface.getHeight() - maxY * this.tileheight) / 2);

        Graphics2D g = surface.createGraphics();

        surface.setAccelerationPriority(1);

        //Tile rendering
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                try {
                    Tile t = tw.getWorldtile(new Coords3D(x, y, 0));
                    Image i = t.render();

                    if (i == null) {
                        continue;
                    }

                    g.drawImage(
                            i,
                            x * drawW + xoff,
                            y * drawH + yoff,
                            drawW,
                            drawH,
                            null);

                } catch (TilesetIndexException ex) {
                    String message = "Found a tile with an invalid index while rendering!\n\t" + ex.getMessage();

                    LOG.log(Level.WARNING, message);

                    if (wrop.hasFlag(Flags.DIE_ON_MISSING_TILES)) {
                        throw new RendererException(message);
                    }
                }
            }
        }
        
        
        ////////////////////////////////////////////////////////////////////////
        //Entity rendering
        //
        //  TODO: This thing is probably totally broken.
        //  TODO: Rewrite completely.
        //
        
        ArrayList<WorldEntity> rentities = new ArrayList<>();

        rentities.addAll(Arrays.asList(tw.getEntities()));

        //sort entities by X for rendering
        Collections.sort(rentities, new SquareGridUtils.EntityXYZComparator(
                SquareGridUtils.Direction.X)
        );

        int rex = 0;    //current array index

        //this is probably the ugliest loop on Earth
        //TODO: redo completely
        for (int x = 0; x < maxX && !rentities.isEmpty(); x++) {
            //entities are already sorted by X, so I can totally do this

            do {
                WorldEntity we = null;
                Coords3D pos = null;
                try {
                    we = rentities.get(rex);
                    if (we == null) {
                        LOG.log(Level.WARNING, "Found a null entity among the entities in this world.");
                    }
                } catch (IndexOutOfBoundsException ex) {
                    LOG.log(Level.SEVERE, "We''re out of bounds somehow. (size:{0}, rex:{1})", new Object[]{rentities.size(), rex});
                    throw ex;
                }

                try {
                    pos = (Coords3D) we.getPos();
                } catch (NullPointerException ex) {
                    LOG.log(Level.SEVERE, "Found an entity with an undefined position.");
                    throw ex;
                }

                if (Math.floor(pos.getCoords()[0]) == x) {
                    Image i = rentities.get(rex++).render();

                    g.drawImage(
                            i,
                            (int) (pos.getX() * drawW + xoff - i.getWidth(null)/2),
                            (int) (pos.getY() * drawH + yoff - i.getHeight(null)/2),
                            (int) (i.getWidth(null) * xscale),
                            (int) (i.getHeight(null) * yscale),
                            null);
                } else {
                    break;
                }
            } while (rex < rentities.size());
        }
    }
}
