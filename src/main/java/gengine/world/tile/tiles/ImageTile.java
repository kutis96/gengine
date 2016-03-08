package gengine.world.tile.tiles;

import gengine.util.coords.Coords3D;
import gengine.world.TiledWorld;
import gengine.world.tile.Tile;
import java.awt.Image;

/**
 * A Tile with a specified texture.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ImageTile extends Tile {

    private final Image img;

    /**
     * Constructs the ImageTile with a specified Image.
     *
     * @param img image to be associated with this tile.
     */
    public ImageTile(Image img) {
        this.img = img;
    }
    
    /**
     * Does nothing, ImageTile is completely static (as in it doesn't animate or
     * react to anything).
     *
     * @param iw See Tile documentation. Not used here.
     * @param pos See Tile documentation. Not used here.
     * @param dt See Tile documentation. Not used here.
     */
    @Override
    public void tick(TiledWorld iw, Coords3D pos, long dt) {
        //do nothing whatsoever
    }

    /**
     * Returns the image belonging to this Tile.
     *
     * @return
     */
    @Override
    public Image render() {
        return img;
    }

}
