package gengine.logic.view;

import gengine.logic.callback.Callback;
import javax.swing.JPanel;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public abstract class View extends JPanel {

    /**
     * Inititalizes the current view.
     *
     * @param onInit a Callback to call once the view is fully initialized and
     *               ready.
     */
    public abstract void initialize(Callback onInit);

    /**
     * Deconstructs the current view, saving everything etc.
     *
     * @param onCompletion a Callback to be called once the deconstruction is
     *                     completed.
     */
    public abstract void deconstruct(Callback onCompletion);
}
