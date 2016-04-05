package sillies.winman;

import gengine.util.coords.Coords2D;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class GWinProperties {
    public Coords2D winpos = new Coords2D(0, 0);
    public Coords2D winsize = new Coords2D(50, 50);
    public String winname = "";
    public boolean visible = true;
    
    public GWinProperties(Coords2D winpos, Coords2D winsize){
        this.winpos = winpos;
        this.winsize = winsize;
    }
}
