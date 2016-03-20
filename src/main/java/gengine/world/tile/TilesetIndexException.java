package gengine.world.tile;

/**
 * An exception thrown on a world type mismatch, typically for rendering.
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
