package gengine.util.coords;

/**
 * An exception thrown on a dimension mismatch of something, typically Coords.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class DimMismatchException extends Exception {
    public DimMismatchException(String message) {
        super(message);
    }
}
