package gengine.world.entity.hitbox;

import gengine.util.coords.*;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Hitbox2D implements Hitbox {

    private static enum Type {
        //TODO: everything.
        CIRCLE,
        BOX,
        POLYGON
    }

    private float rot = 0.F;
    private final Type type;
    private CoordsFixedD[] coords = new CoordsFixedD[1];

    public Hitbox2D(float radius) {
        this.type = Type.CIRCLE;
        this.coords[0] = new CoordsFixedD(new float[]{radius});
    }

    public Hitbox2D(float width, float height) {
        this.type = Type.BOX;
        this.coords[0] = new CoordsFixedD(new float[]{width, height});
    }

    public Hitbox2D(Coords2D[] points) {
        this.type = Type.POLYGON;
        this.coords = points;
    }

    @Override
    public boolean hits(Hitbox hitbox, Coords3D offset) {
        //TODO
        return false;
    }

    @Override
    public boolean isWithin(Coords3D point) {
        //TODO
        return false;
    }

    @Override
    public Coords3D[] getOutermost() {
        //TODO
        return null;
    }

    @Override
    public void setRotation(float rot) {
        throw new UnsupportedOperationException("LATER!");
        //this.rot = rot;
    }

    @Override
    public float getRotation() {
        return this.rot;
    }

}