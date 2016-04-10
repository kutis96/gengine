package gengine.winman;

import gengine.util.coords.Coords2D;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * A simple window manager to be used within the engine. Its performance is
 * probably far from ideal, but it also indirectly supports transparentish
 * windows and whatnot.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WinMan {

    private class SurfacePosTuple {

        private final GSurface surface;
        private Coords2D pos;
        private long zpos;

        public SurfacePosTuple(GSurface surface, Coords2D pos, long zpos) {
            this.surface = surface;
            this.pos = pos;
            this.zpos = zpos;
        }

        public GSurface getSurface() {
            return this.surface;
        }

        public Coords2D getPos() {
            return this.pos;
        }

        public void setPos(Coords2D pos) {
            this.pos = pos;
        }

        public void setZpos(long z) {
            this.zpos = z;
        }

        public long getZpos() {
            return this.zpos;
        }
    }

    private ArrayList<SurfacePosTuple> surfaces;
    private final JPanel window;

    public WinMan(JPanel window) {
        this.surfaces = new ArrayList<>();
        this.window = window;
    }

    public void addSurface(GSurface s, Coords2D pos) {
        this.surfaces.add(new SurfacePosTuple(s, pos, surfaces.size()));
    }

    public boolean closeSurface(GSurface s) {
        SurfacePosTuple st = this.getSurfacePosTuple(s);

        return (st == null) ? false : this.surfaces.remove(st);
    }

    public boolean setSurfaceVisibility(GSurface s, boolean vis) {
        SurfacePosTuple st = this.getSurfacePosTuple(s);

        if (st == null) {
            return false;
        } else {
            st.getSurface().setVisible(vis);
            return true;
        }
    }

    public boolean resizeSurface(GSurface s, Coords2D size) {
        SurfacePosTuple st = this.getSurfacePosTuple(s);

        if (st == null) {
            return false;
        } else {
            st.getSurface().setSize((int) size.getX(), (int) size.getY());
            return true;
        }
    }

    public GSurface getTopSurface() {
        long maxz = -1;
        GSurface maxs = null;

        for (SurfacePosTuple st : this.surfaces) {
            if (st.getZpos() > maxz) {
                maxz = st.getZpos();
                maxs = st.getSurface();
            }
        }

        return maxs;
    }

    /**
     * Looks for a GSurface that can be seen by say a cursor at the given point.
     *
     * @param point Cursor position
     *
     * @return GSurface if found, null otherwise.
     */
    public GSurface getVisibleSurface(Coords2D point) {
        ArrayList<SurfacePosTuple> couldbe = new ArrayList<>();

        for (SurfacePosTuple st : this.surfaces) {
            if (st.getSurface().isVisible() && isWithin(point, st.getPos(), dim2coords(st.getSurface().getSize()))) {
                couldbe.add(st);
            }
        }

        ArrayList<SurfacePosTuple> couldverywellbe = new ArrayList<>();

        for (SurfacePosTuple st : couldbe) {
            if (st.getSurface().hits((Coords2D) point.subtract(st.getPos()))) {
                couldverywellbe.add(st);
            }
        }

        long maxz = -1;
        GSurface theone = null;

        for (SurfacePosTuple st : couldverywellbe) {
            if (st.getZpos() > maxz) {
                maxz = st.getZpos();
                theone = st.getSurface();
            }
        }

        return theone;
    }

    /**
     * @param point  point position
     * @param offset offset of the rectangle (its 0,0 point position)
     * @param size   size of the rectangle
     *
     * @return true if point happens to be withing a rectangle starting at
     *         offset and being of size size.
     */
    private boolean isWithin(Coords2D point, Coords2D offset, Coords2D size) {
        return (point.getX() > offset.getX() && point.getX() < (offset.getX() + size.getX()))
                && (point.getY() > offset.getY() && point.getY() < (offset.getY() + size.getY()));
    }

    /**
     * Converts Dimension to Coords2D.
     *
     * @param dim Dimension to convert.
     *
     * @return converted Coords2D.
     */
    private Coords2D dim2coords(Dimension dim) {
        return new Coords2D(dim.width, dim.height);
    }

    /**
     * 'Searches' for a SurfacePosTuple with supplied GSurface as its surface.
     *
     * @param s GSurface to look for in the SurfacePosTuple array
     *
     * @return SurfacePosTuple containing the GSurface. Returns null if no
     *         SurfacePosTuple was found containing the given GSurface.
     */
    private SurfacePosTuple getSurfacePosTuple(GSurface s) {
        for (SurfacePosTuple st : this.surfaces) {
            if (st.getSurface() == s) {
                return st;
            }
        }
        return null;
    }

}
