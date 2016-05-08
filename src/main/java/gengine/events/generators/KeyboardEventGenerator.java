package gengine.events.generators;

import gengine.events.receivers.KeyboardEventReceiver;
import gengine.world.World;
import gengine.world.entity.WorldEntity;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * KeyboardEventGenerator. Listens to KeyEvents generated by the game's JPanel,
 * and dispatches them throughout the world.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class KeyboardEventGenerator extends AbstWindowEventGenerator {

    private static final Logger LOG = Logger.getLogger(KeyboardEventGenerator.class.getName());

    //TODO: possibly rewrite this thing to use some silly FIFO buffers to store the KeyEvents
    //      and then dispatch them one by one inside the work method
    //
    private final World world;
    private final JPanel jp;

    private final KeyListener kl = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            dispatchKeyEvent(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            dispatchKeyEvent(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            dispatchKeyEvent(e);
        }
    };

    public KeyboardEventGenerator(World world, JPanel jprame, int period) {
        super(world, jprame, period);
        this.world = world;
        this.jp = jprame;
    }

    @Override
    public void init() {
        //add a new KeyListener to the JPanel

        this.jp.addKeyListener(kl);
        
        LOG.info("Initialized");
    }

    @Override
    public void work(long dt) {
        //periodically check the world entities hash
        //if it doesn't match, rebuild the event generator list?
        //somewhat pointless.
    }

    @Override
    public void die() {
        //detach KeyListener from the JPanel
        this.jp.removeKeyListener(kl);
        
        LOG.info("Dead.");
    }

    private void dispatchKeyEvent(KeyEvent e) {
        LOG.log(Level.FINE, "KEG Event: {0}", e);
        
        synchronized (this.world) {
            WorldEntity[] worldEntities = this.world.getEntities();

            for (WorldEntity we : worldEntities) {
                if (we instanceof KeyboardEventReceiver) {
                    KeyboardEventReceiver receiver = (KeyboardEventReceiver) we;
                    sendToReceiver(e, receiver);
                }
            }
        }

    }

    private void sendToReceiver(KeyEvent e, KeyboardEventReceiver receiver) {
        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED: {
                receiver.keyPressed(e);
                break;
            }
            case KeyEvent.KEY_RELEASED: {
                receiver.keyReleased(e);
                break;
            }
            case KeyEvent.KEY_TYPED: {
                receiver.keyTyped(e);
                break;
            }
            default: {
                LOG.severe("Unexpected KeyEvent ID! This is seriously fishy.");
            }
        }
    }

}
