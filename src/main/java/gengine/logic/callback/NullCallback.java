package gengine.logic.callback;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class NullCallback implements Callback {

    @Override
    public void callbackOnError(Exception ex) {
        //do nothing
    }

    @Override
    public void callback() {
        //do nothing
    }
}
