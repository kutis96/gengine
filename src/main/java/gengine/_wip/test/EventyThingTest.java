package gengine._wip.test;

import gengine._wip.test.ent.*;
import gengine.events.generators.*;
import gengine.util.loaders.TiledWorldLoader;
import gengine.util.loaders.TilesetLoader;
import gengine.logic.Controller;
import gengine.logic.Simulator;
import gengine.rendering.WorldRenderer;
import gengine.rendering.squaregrid.SquareGridRenderer;
import gengine.util.UnsupportedFormatException;
import gengine.util.coords.*;
import gengine.world.*;
import gengine.world.tile.Tileset;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class EventyThingTest {

    public static void main(String[] args) throws WorldSizeException, IOException, UnsupportedFormatException, InterruptedException, DimMismatchException, ValueException {

        JFrame window = new JFrame();

        window.setTitle("Random Gamish Thing");
        window.setSize(1024, 768);
        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tileset ts = TilesetLoader.load(new File("/home/rkutina/testing/gengine/world.tset"));
        World w = TiledWorldLoader.load(new File("/home/rkutina/testing/gengine/world.wrld"), ts);

        WorldRenderer wren = new SquareGridRenderer(64);

        Simulator sim = new Simulator(w, 50);

        Ball b1 = new Ball();
        //BallThing b2 = new BallThing();
        MovableBall b3 = new MovableBall();

        b1.setPos(new Coords3D(4, 4, 0));
        //b2.setPos(new Coords3D(5,5,0));
        b3.setPos(new Coords3D(3, 3, 0));

        w.addEntity(b1);
        //w.addEntity(b2);
        w.addEntity(b3);

        AbstEventGenerator[] evgens = {new ProximityEventGenerator(w, 10), new KeyboardEventGenerator(w, window, 50)};

        Controller c = new Controller(sim, w, window, wren, evgens);

        c.start();

        Thread.sleep(30000);

        c.stop();

        Thread.sleep(100);
        System.exit(0);
    }
}
