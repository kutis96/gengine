package demo.ent;

import gengine.events.receivers.MouseEventReceiver;
import gengine.events.receivers.ProximityEventReceiver;
import gengine.logic.facade.TiledWorldFacade;
import gengine.logic.facade.WorldControllerFacade;
import gengine.rendering.overlay.*;
import gengine.util.coords.Neco3D;
import gengine.util.coords.NecoUtils;
import gengine.world.entity.WorldEntity;
import gengine.world.entity.TiledWorldEntity;
import gengine.world.tile.Tile;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A random ball entity used for testing only.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class ScaredBall extends TiledWorldEntity implements ProximityEventReceiver, MouseEventReceiver, HasOverlays {

    private static final Logger LOG = Logger.getLogger(ScaredBall.class.getName());

    private final Image img_scared;
    private final Image img_normal;

    private TextOverlay olay;
    
    private enum State {
        NORMAL,
        PRE_NORMAL,
        FLIGHT,
        DEAD;
    };

    private volatile State intstate = State.NORMAL;
    private volatile long msSinceLastStateChange = 0;
    private long msSinceLastMovement = 0;

    private static int flee_for = 2000; //ms

    private Neco3D fleeFrom = new Neco3D();

    private Neco3D velocity = new Neco3D();

    private WorldControllerFacade facade;

    public ScaredBall(WorldControllerFacade facade) throws IOException {
        super(facade);
        this.facade = facade;

        this.img_scared = ImageIO.read(this.getClass().getResource("/demo/entity/greenball.png"));
        this.img_normal = ImageIO.read(this.getClass().getResource("/demo/entity/blueball.png"));

        this.olay = new TextOverlay("", new Neco3D(new int[]{0, 0, 100}, true));
    }

    @Override
    public int getState() {
        return (intstate == State.DEAD ? 0xDEAD : intstate.ordinal());
    }

    @Override
    public void resetState() {
        this.intstate = State.NORMAL;
    }

    @Override
    public void tick(long dt) {

        msSinceLastStateChange += dt;
        msSinceLastMovement += dt;

        switch (intstate) {
            case DEAD: {
                break;
            }

            case FLIGHT: {

                flee(dt);

                if (msSinceLastStateChange > flee_for) {
                    changeState(State.PRE_NORMAL);
                }
            }
            break;

            case PRE_NORMAL: {
                this.olay.setText("Whew.");
                changeState(State.NORMAL);
            }
            break;

            case NORMAL: {
                wander();

                if (msSinceLastStateChange > flee_for) {
                    this.olay.setText("");
                    changeState(State.NORMAL);
                }
            }
            break;
        }

        TiledWorldFacade twf = (TiledWorldFacade) this.facade.getWorldFacade();

        Tile t = twf.getTile(this.getPos());

        if (t == null || t.isWall()) {
            changeState(State.DEAD);
            this.olay.setText("Dead.");
        }

    }

    private void changeState(State state) {
        if (this.intstate != State.DEAD) {
            this.intstate = state;
            this.msSinceLastStateChange = 0;
        }
    }

    private void flee(long dt) {
        Neco3D nvel = new Neco3D(this.fleeFrom)
                .sub(this.getPos())
                .normalize().multiplyI(-3);

        Neco3D jitter = NecoUtils.generateRandom(
                new Neco3D(new int[]{-Neco3D.N_PER_UNIT / 2, -Neco3D.N_PER_UNIT / 2, 0}, false),
                new Neco3D(new int[]{1, 1, 0}, true).multiplyI(5));

        //nvel.increment(jitter);
        this.velocity = nvel;
        this.applyvelocity(dt);
    }

    private void wander() {
        //wander~
    }

    private void applyvelocity(long dt) {

        this.advancePos(
                velocity.multiplyD((double) dt / 1000.),
                true,
                true
        );
    }

    @Override
    public boolean advancePos(Neco3D pos, boolean checkForMissingTiles, boolean checkForWalls) {
        this.msSinceLastMovement = 0;
        return super.advancePos(pos, checkForMissingTiles, checkForWalls);
    }

    @Override
    public void advancePos(Neco3D pos) {
        this.msSinceLastMovement = 0;
        super.advancePos(pos);
    }

    @Override
    public boolean advancePos(Neco3D pos, boolean checkForMissingTiles) {
        this.msSinceLastMovement = 0;
        return super.advancePos(pos, checkForMissingTiles);
    }

    @Override
    public Image render() {
        switch (intstate) {
            case FLIGHT:
                return img_scared;
            default:
            case NORMAL:
                return img_normal;
        }
    }

    @Override
    public void onProxEvent(WorldEntity closeEntity, float distance) {

        this.olay.setText(" Aaaah!"
                + "\nRun away!");

        this.fleeFrom = closeEntity.getPos();

        this.changeState(State.FLIGHT);

        this.msSinceLastMovement = 0;
        this.msSinceLastStateChange = 0;
    }

    @Override
    public float getProxDistance() {
        return (float) 2.;
    }

    @Override
    public boolean mouseHit(Point point) {
        return Point.distance(point.x, point.y, 0, 0) <= this.img_normal.getHeight(null) / 2;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        this.changeState(State.FLIGHT);

        this.olay.setText("IT CLICKED ME!");

        this.fleeFrom = this.getPos().roundAll();

        //this.msSinceLastMovement = 0;
        //this.msSinceLastStateChange = 200;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Not really required
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Not used
    }

    @Override
    public Overlay[] getOverlays() {
        return new Overlay[]{this.olay};
    }

    @Override
    public void attachOverlay(Overlay overlay) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void animateOverlays(long dt) {
        //do nothing
    }

}
