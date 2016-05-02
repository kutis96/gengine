package gengine.world;

/**
 * An Exception thrown when the worldsize happens to be of an illegal value.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class WorldSizeException extends Exception {

    public WorldSizeException(String message) {
        super(message);
    }
}
