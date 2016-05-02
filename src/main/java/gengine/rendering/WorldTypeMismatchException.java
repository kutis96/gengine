package gengine.rendering;

/**
 * An exception thrown on a world type mismatch, typically for rendering.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class WorldTypeMismatchException extends Exception {
    public WorldTypeMismatchException(String message) {
        super(message);
    }

    public WorldTypeMismatchException() {
        super();
    }
}
