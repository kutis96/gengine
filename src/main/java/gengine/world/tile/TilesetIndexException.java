package gengine.world.tile;

/**
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
