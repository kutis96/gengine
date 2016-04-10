package gengine.world.entity;

import gengine.events.EventListener;
import gengine.events.Event;
import gengine.iwishjavahadtraits.*;
import gengine.util.coords.*;
import gengine.world.World;
import java.util.ArrayList;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldEntity implements Positionable, Renderable, HasListeners {

    private Coords3D pos;

    private ArrayList<EventListener> listeners;

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
     * @throws gengine.util.coords.DimMismatchException
     */
    @Override
    public void setPos(Coords pos) throws DimMismatchException {
        if (pos == null || pos.getDimensions() != 3) {
            throw new DimMismatchException();
        }
        this.pos = (Coords3D) pos;
    }

    /**
     * Ticks (updates) the entity.
     *
     * @param w  The world this entity resides in.
     * @param dt Delta-tee in milliseconds (time since the last update).
     */
    public abstract void tick(World w, long dt);

    @Override
    public void attachListener(EventListener c) {
        this.listeners.add(c);
    }

    @Override
    public void clearListeners() {
        this.listeners.clear();
    }

    @Override
    public void dispatchEvent(Event e) {
        for(EventListener c : this.listeners){
            c.dispatchEvent(e);
        }
    }
}
