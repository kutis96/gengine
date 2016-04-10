package gengine.rendering.squaregrid;

import gengine.rendering.*;
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
    public void render(World world, BufferedImage surface, WorldRendererOptions wropt) throws WorldTypeMismatchException, RendererException {
        if (world instanceof TiledWorld) {
            this.render((TiledWorld) world, surface, wropt);
        } else {
            throw new WorldTypeMismatchException();
        }
    }

    private void render(TiledWorld tw, BufferedImage surface, WorldRendererOptions wrop) throws RendererException {
        int drawh = (int) (this.tileheight * wrop.zoom);
        int draww = (int) (this.tilewidth * wrop.zoom);

        int max_x = (int) tw.getWorldSize().getX();
        int max_y = (int) tw.getWorldSize().getY();
        
        float xscale = this.tileheight/drawh;
        float yscale = this.tileheight/drawh;

        int xoff = (int) (wrop.cameraPosition.getX() * draww + wrop.cameraOffset.getX() + (surface.getWidth() - max_x * this.tilewidth) / 2);
        int yoff = (int) (wrop.cameraPosition.getY() * drawh + wrop.cameraOffset.getY() + (surface.getHeight() - max_y * this.tileheight) / 2);

        Graphics2D g = surface.createGraphics();

        surface.setAccelerationPriority(1);

        //Tile rendering
        for (int x = 0; x < max_x; x++) {
            for (int y = 0; y < max_y; y++) {
                try {
                    Tile t = tw.getWorldtile(new Coords3D(x, y, 0));
                    Image i = t.render();

                    if (i == null) {
                        continue;
                    }

                    g.drawImage(
                            i,
                            x * draww + xoff,
                            y * drawh + yoff,
                            draww,
                            drawh,
                            null);

                } catch (TilesetIndexException ex) {
                    String message = "Found a tile with an invalid index while rendering!\n\t" + ex.getMessage();

                    LOG.log(Level.WARNING, message);

                    if ((wrop.flags & WorldRendererOptions.RenderFlags.DIE_ON_MISSING_TILES.getFlagValue()) != 0) {
                        throw new RendererException(message);
                    }
                }
            }
        }

        ArrayList<WorldEntity> rentities = new ArrayList<>();

        rentities.addAll(Arrays.asList(tw.getEntities()));

        //sort entities by X for rendering
        Collections.sort(rentities, new SquareGridUtils.EntityXComparator());

        //Entity rendering
        int rex = 0;    //current array index

        //this is probably the ugliest loop on Earth
        mainfor:
        for (int x = 0; x < max_x & rentities.size() > 0; x++) {
            //entities are already sorted by X, so I can totally do this

            do {
                WorldEntity we = null;
                Coords3D pos = null;
                try {
                    we = rentities.get(rex);
                    if (we == null) {
                        REthrower("Found a null entity among the entities in this world.");
                    }
                } catch (IndexOutOfBoundsException ex) {
                    REthrower("We're out of bounds somehow. (size:" + rentities.size() + ", rex:" + rex + ")");
                }

                try {
                    pos = (Coords3D) we.getPos();
                } catch (NullPointerException ex) {
                    REthrower("Found an entity with undefined position.");
                }

                if (Math.floor(pos.getCoords()[0]) == x) {
                    Image i = rentities.get(rex++).render();

                    g.drawImage(
                            i,
                            (int) (pos.getX() * draww + xoff - i.getWidth(null)/2),
                            (int) (pos.getY() * drawh + yoff - i.getHeight(null)/2),
                            (int) (i.getWidth(null) * xscale),
                            (int) (i.getHeight(null) * yscale),
                            null);
                } else {
                    continue mainfor;
                }
            } while (rex < rentities.size());

            break;
        }
    }

    private void REthrower(String msg) throws RendererException {
        LOG.log(Level.SEVERE, msg);
        throw new RendererException(msg);
    }
}
