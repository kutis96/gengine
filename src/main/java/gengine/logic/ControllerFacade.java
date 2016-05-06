package gengine.logic;

import gengine.rendering.WorldTypeMismatchException;
import gengine.util.coords.Coords3D;
import gengine.util.facade.WorldFacade;
import gengine.world.World;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface ControllerFacade {

    /**
     * Changes the camera's focus point.
     *
     * @param pos
     */
    public void changeCamPosition(Coords3D pos);

    /**
     * Gets the WorldFacade to be used by various entities.
     *
     * @return WorldFacade returned by the current world
     */
    public WorldFacade getWorldFacade();

    /**
     * Tries to switch to a different world.
     *
     * @param requestor An object, typically WorldEntity, requesting this
     *                  operation
     * @param world     World to switch into
     * @param save      when true, the current world would still be kept in
     *                  memory, so it's easy to return back to it. when false,
     *                  the new world would completely overwrite the old one.
     *
     * @return true on a successful switch
     *
     * @throws WorldTypeMismatchException
     */
    public boolean switchWorlds(Object requestor, World world, boolean save) throws WorldTypeMismatchException;

    /**
     * Tries to return to the last saved world.
     *
     * @param requestor An object, typically WorldEntity, requesting this
     *                  operation
     *
     * @return true on a successful switch
     */
    public boolean returnWorlds(Object requestor);
}
