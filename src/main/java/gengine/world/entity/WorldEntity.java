package gengine.world.entity;

import gengine.ctrls.Controller;
import gengine.ctrls.events.Event;
import gengine.iwishjavahadtraits.*;
import gengine.util.coords.Coords;
import gengine.util.coords.Coords3D;
import gengine.world.World;
import java.util.ArrayList;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldEntity implements Positionable, Renderable, Controllable {

    private Coords3D pos;

    private ArrayList<Controller> controllers;

    /**
     * Gets the WorldEntity's position
     *
     * @return coordinates of this entity
     */
    @Override
    public Coords getPos() {
        return this.pos;
    }

    /**
     * Sets the WorldEntity's new position
     *
     * @param pos
     *
     * @return returns true on success, false on failure.
     */
    @Override
    public boolean setPos(Coords pos) {
        if (pos == null || pos.getDimensions() != 3) {
            return false;
        }
        this.pos = (Coords3D) pos;

        return true;
    }

    /**
     * Ticks (updates) the entity.
     *
     * @param w  The world this entity resides in.
     * @param dt Delta-tee in milliseconds (time since the last update).
     */
    public abstract void tick(World w, long dt);

    @Override
    public void attachController(Controller c) {
        this.controllers.add(c);
    }

    @Override
    public void clearControllers() {
        this.controllers.clear();
    }

    @Override
    public void dispatchEvent(Event e) {
        for(Controller c : this.controllers){
            c.dispatchEvent(e);
        }
    }
}
