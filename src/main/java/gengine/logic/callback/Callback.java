package gengine.logic.callback;

/**
 * A generic callback.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface Callback {

    public void callback();
    
    public void callbackOnError(Exception ex);
    
}
