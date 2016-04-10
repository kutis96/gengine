package gengine.world.tile;

/**
 * An exception thrown either on an index exceeding the Tileset index boundaries
 * or on an unknown index within the Tileset.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class TilesetIndexException extends Exception {

    public TilesetIndexException(String message) {
        super(message);
    }

    public TilesetIndexException() {
        super();
    }
}
