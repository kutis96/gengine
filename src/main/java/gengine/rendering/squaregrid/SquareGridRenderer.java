package gengine.rendering.squaregrid;

import gengine.rendering.*;
import gengine.util.coords.Coords3D;
import gengine.world.TiledWorld;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.Tile;
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
    public void render(World world, BufferedImage surface, WorldRendererOptions wropt) throws WorldTypeMismatchException {
        if (world instanceof TiledWorld) {
            this.render((TiledWorld) world, surface, wropt);
        } else {
            throw new WorldTypeMismatchException();
        }
    }

    private void render(TiledWorld tw, BufferedImage surface, WorldRendererOptions wropt) {
        int drawh = (int) (this.tileheight * wropt.zoom);
        int draww = (int) (this.tilewidth * wropt.zoom);

        int max_x = (int) tw.getWorldSize().getX();
        int max_y = (int) tw.getWorldSize().getY();

        int xoff = (int) (wropt.cameraPosition.getX() * draww + wropt.cameraOffset.getX() + surface.getWidth() / 2);
        int yoff = (int) (wropt.cameraPosition.getY() * drawh + wropt.cameraOffset.getY() + surface.getHeight() / 2);

        Graphics2D g = surface.createGraphics();

        surface.setAccelerationPriority(1);

        ArrayList<WorldEntity> rentities = new ArrayList<>();

        rentities.addAll(Arrays.asList(tw.getEntities()));

        //sort entities by X for rendering
        Collections.sort(rentities, new SquareGridUtils.EntityXComparator());

        //Tile rendering
        for (int x = 0; x < max_x; x++) {
            for (int y = 0; y < max_y; y++) {
                Tile t = tw.getWorldtile(new Coords3D(x, y, 0));
                Image i = t.render();

                g.drawImage(
                        i,
                        x * draww + xoff,
                        y * drawh + yoff,
                        draww,
                        drawh,
                        null);
            }
        }

        int rex = 0;

        //Entity rendering
        for (int x = 0; x < max_x; x++) {
            try {
                //entities are already sorted by X, so I can do this
                for (; ((int) rentities.get(rex).getPos().getCoords()[0] == x) && (rex < rentities.size()); rex++) {
                    Image i = rentities.get(rex).render();
                }
            } catch (NullPointerException nex) {
                Logger.getLogger(SquareGridRenderer.class.getName()).log(Level.WARNING, "Found a Null in the entity rendering bit.");
            }
        }
    }
}
