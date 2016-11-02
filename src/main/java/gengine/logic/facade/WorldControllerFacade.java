package gengine.logic.facade;

import gengine.rendering.WorldTypeMismatchException;
import gengine.util.coords.Neco3D;
import gengine.world.World;
import gengine.world.entity.WorldEntity;

/**
 * A facade distributed to WorldEntities in order to provide some access to some
 * of the controller's functions, and possibly to the world around them.
 *
 * I have chosen this somewhat indirect method, as it enables for some
 * decoupling between the actual controller logic and what is provided to the
 * entities themselves.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface WorldControllerFacade {

    /**
     * Changes the camera's focus point.
     *
     * @param pos
     */
    public void changeCamPosition(Neco3D pos);

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
     * operation
     * @param world World to switch into
     * @param save when true, the current world would still be kept in memory,
     * so it's easy to return back to it. when false, the new world would
     * completely overwrite the old one.
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
     * operation
     *
     * @return true on a successful switch
     */
    public boolean returnWorlds(Object requestor);

    /**
     * Adds an entity into the world.
     *
     * @param we WorldEntity to spawn.
     *
     * @return true on success, false never.
     */
    public boolean spawnEntity(WorldEntity we);
}
