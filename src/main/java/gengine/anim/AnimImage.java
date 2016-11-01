package gengine.anim;

import java.awt.Image;

/**
 * A simple interface to be shared by all animated images.
 * 
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface AnimImage {
    public void tick(long dt);
    
    public Image getCurrentImage();
}
