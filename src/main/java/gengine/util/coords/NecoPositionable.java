package gengine.util.neco;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface NecoPositionable {

    /**
     * Gets the object's coordinates.
     *
     * @return the object's coordinates
     */
    public Neco3D getPos();

    /**
     * Sets the object's position. Should return false on failure - i.e. when
     * some objects are meant to be static for some reason.
     *
     * @param pos The new coordinates
     *
     * @throws DimMismatchException thrown on dimension mismatch
     */
    public void setPos(Neco3D pos);
}
