package gengine.rendering.squaregrid;

import gengine.rendering.*;
import gengine.util.coords.Coords3D;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.Tile;
import gengine.world.tile.TilesetIndexException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SquareGridRenderer implements WorldRenderer {

    //TODO: redo this mess.
    private static final Logger LOG = Logger.getLogger(SquareGridRenderer.class.getName());

    private final int tilewidth;
    private final int tileheight;
    
    private double[][] trmat = new double[2][3];

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

        //prescaled tile drawing width and height
        int drawh = (int) (this.tileheight * wrop.zoom);
        int draww = (int) (this.tilewidth * wrop.zoom);

        int max_x = (int) tw.getWorldSize().getX();
        int max_y = (int) tw.getWorldSize().getY();

        //float xscale = this.tileheight/drawh;
        //float yscale = this.tileheight/drawh;
        //scaling constants for entity drawing - I will not bother with this just yet
        float xscale = 1;
        float yscale = 1;

        //rendering offset
        int xoff = (int) (wrop.cameraPosition.getX() * draww + wrop.cameraOffset.getX());
        int yoff = (int) (wrop.cameraPosition.getY() * drawh + wrop.cameraOffset.getY());
        
        this.trmat[0] = new double[]{(double)xoff,  (double)yoff,  0.};
        this.trmat[1] = new double[]{(double)drawh, (double)draww, 0.};

        //int xoff = (int) (wrop.cameraPosition.getX() * draww + wrop.cameraOffset.getX() + (surface.getWidth() - max_x * this.tilewidth) / 2);
        //int yoff = (int) (wrop.cameraPosition.getY() * drawh + wrop.cameraOffset.getY() + (surface.getHeight() - max_y * this.tileheight) / 2);
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
        Collections.sort(rentities, new SquareGridUtils.EntityYComparator());

        //Entity rendering
        int rex = 0;    //current array index

        //this is probably the ugliest loop on Earth
        mainfor:
        for (int y = 0; y < max_y & rentities.size() > 0; y++) {
            //entities are already sorted by Y, so I can totally do this

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

                if (Math.floor(pos.getCoords()[1]) == y) {
                    //actual entity image drawing here

                    Image i = we.render();

                    g.drawImage(
                            i,
                            (int) (pos.getX() * draww + xoff - i.getWidth(null) / 2),
                            (int) (pos.getY() * drawh + yoff - i.getHeight(null) / 2),
                            (int) (i.getWidth(null) * xscale),
                            (int) (i.getHeight(null) * yscale),
                            null);

                    rex++;
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

    @Override
    public Coords3D getWorldCoords(World world, Point point) throws WorldTypeMismatchException, RendererException {

        //TODO: actually use some global matrix of this stuff to transform between a Point to the Coords3D based on the last render
        //
        // matrix structure:
        //  (xoff xscale)
        //  (yoff yscale)
        //  (zoff zscale)

        float px = (float) ((point.getX() - this.trmat[0][0]) / this.trmat[1][0]);
        float py = (float) ((point.getY() - this.trmat[0][1]) / this.trmat[1][1]);

        return new Coords3D(px, py, 0);
    }
}
