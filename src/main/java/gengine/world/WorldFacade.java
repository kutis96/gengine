package gengine.world;

/**
 * A façade masking a World's methods for guaranteed safe access by the
 * WorldEntities residing within the World itself. I'm trying to avoid giving
 * the WorldEntities a direct reference to the World itself, as that may prove
 * to be bonkers later on.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldFacade {

}