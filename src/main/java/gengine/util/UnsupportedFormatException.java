package gengine.util;

/**
 * An Exception thrown when the worldsize happens to be of an illegal value.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class UnsupportedFormatException extends Exception {

    public UnsupportedFormatException(String message) {
        super(message);
    }
}
