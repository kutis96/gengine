package gengine.logic.controller;

import gengine.logic.callback.NullCallback;
import gengine.logic.view.View;
import gengine.util.Worker;
import javax.swing.JFrame;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class MainController extends Worker {

    private final Object lock;

    private View currentView;
    private final JFrame mainFrame;
    
    public MainController(JFrame mainFrame, int period) {
        super(period);
        this.lock = new Object();
        this.mainFrame = mainFrame;
    }

    public void setView(View view) {
        synchronized (lock) {
            view.initialize(new NullCallback());
            this.currentView.deconstruct(new NullCallback());
            this.currentView = view;
        }
        
        this.mainFrame.removeAll();
        this.mainFrame.add(view);
        this.mainFrame.doLayout();
    }

    public View getView() {
        synchronized (lock) {
            return this.currentView;
        }
    }
}
