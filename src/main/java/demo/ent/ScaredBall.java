package demo.ent;

import gengine.events.receivers.MouseEventReceiver;
import gengine.events.receivers.ProximityEventReceiver;
import gengine.logic.facade.TiledWorldFacade;
import gengine.logic.facade.WorldControllerFacade;
import gengine.rendering.overlay.*;
import gengine.util.coords.Coords3D;
import gengine.util.coords.DimMismatchException;
import gengine.util.coords.ValueException;
import gengine.world.entity.WorldEntity;
import gengine.world.entity.TiledWorldEntity;
import gengine.world.tile.Tile;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
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
        DEAD
    };

    private volatile State intstate = State.NORMAL;
    private volatile long msSinceLastStateChange = 0;
    private long msSinceLastMovement = 0;

    private static int flee_for = 2000; //ms
    
    private Coords3D fleeFrom = new Coords3D();
    
    private Coords3D velocity = new Coords3D();

    private WorldControllerFacade facade;

    public ScaredBall(WorldControllerFacade facade) throws IOException {
        super(facade);
        this.facade = facade;

        this.img_scared = ImageIO.read(this.getClass().getResource("/demo/entity/greenball.png"));
        this.img_normal = ImageIO.read(this.getClass().getResource("/demo/entity/blueball.png"));

        this.olay = new TextOverlay("", new Coords3D(0, 0, 9000));
    }

    @Override
    public int getState() {
        return intstate.ordinal();
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
            case FLIGHT: {
                
                
                flee(dt);

                if (msSinceLastStateChange > flee_for) {
                    changeState(State.PRE_NORMAL);
                }
                
                this.olay.setText(this.velocity.toString());
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
            this.intstate = State.DEAD;
        }

    }

    private void changeState(State state) {
        this.intstate = state;
        this.msSinceLastStateChange = 0;
    }
    
    private void flee(long dt) {
        if(msSinceLastMovement > 30){ 
            try {
                
                Coords3D nvel = new Coords3D(this.fleeFrom);
                nvel.subtract(this.getPos());
                nvel.normalize();
                //nvel.multiply((float) -1);
                
                this.velocity = nvel;

                this.applyvelocity(dt);
                
            } catch (ValueException | DimMismatchException ex) {
                LOG.log(Level.SEVERE, "This is bad", ex);
            }
        }
    }

    private void wander() {
        //wander~
    }
    
    private void applyvelocity(long dt){
        try {
            this.advancePos((Coords3D) new Coords3D(velocity).multiply((float)(dt/1000.)));
        } catch (ValueException ex) {
            LOG.log(Level.SEVERE, "This is bad indeed", ex);
        }
    }

    
    @Override
    public boolean advancePos(Coords3D pos, boolean checkForMissingTiles, boolean checkForWalls) {
        this.msSinceLastMovement = 0;
        return super.advancePos(pos, checkForMissingTiles, checkForWalls);
    }

    @Override
    public void advancePos(Coords3D pos) {
        this.msSinceLastMovement = 0;
        super.advancePos(pos);
    }
    
    @Override
    public boolean advancePos(Coords3D pos, boolean checkForMissingTiles) {
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
        return (float) 1.5;
    }

    @Override
    public boolean mouseHit(Point point) {
        return Point.distance(point.x, point.y, 0, 0) <= this.img_normal.getHeight(null) / 2;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        this.changeState(State.FLIGHT);

        this.olay.setText("IT CLICKED ME!");
        
        this.fleeFrom = this.getPos();

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
