package gengine.util;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class Visible {
    
    private boolean visible;
    
    public Visible(){
        this(true);
    }
    
    public Visible(boolean vis){
        this.visible = vis;
    }
    
    public boolean isVisible(){
        return visible;
    }
    public boolean setVisible(boolean vis){
        return this.visible;
    }
}
