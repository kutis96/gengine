package gengine.util;

/**
 * An Exception thrown when a file is found to be of an unsupported format.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class UnsupportedFormatException extends Exception {

    public UnsupportedFormatException(String message) {
        super(message);
    }
}
