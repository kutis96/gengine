package gengine.world.entity;

import gengine.world.entity.hitbox.Hitbox;
import gengine.ctrls.ent.EntityController;
import gengine.ctrls.ent.Event;
import gengine.iwishjavahadtraits.*;
import gengine.util.coords.*;
import gengine.world.World;
import java.util.ArrayList;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public abstract class WorldEntity implements Positionable, Renderable {

    private Coords3D pos;

    private ArrayList<EntityController> controllers;
    
    private Hitbox hb;

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

    public Hitbox getHitbox(){
        return this.hb;
    }

    public void attachController(EntityController c) {
        this.controllers.add(c);
    }


    public void clearControllers() {
        this.controllers.clear();
    }

    public void dispatchEvent(Event e) {
        for(EntityController c : this.controllers){
            c.dispatchEvent(e);
        }
    }
    
    /*
    
    //mouse events
    public abstract void onMouseClick(MouseClickEvent e);
    public abstract void onMouseHover(MouseHoverEvent e);
    public abstract void onMouseDrag(MouseDragEvent e);
    public abstract void onMouseEnter(MouseHoverEvent e);
    public abstract void onMouseLeave(MouseHoverEvent e);
    
    //item use events
    public abstract void onUse(ItemUseEvent e);
    public abstract void onHit(HitEvent e);
    
    //world events
    public abstract void onCollide(CollisionEvent e);
    public abstract void onWorldChange(WorldChangeEvent e);
    
    */
}
