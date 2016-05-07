package demo;

import demo.ent.*;
import gengine.events.generators.*;
import gengine.logic.*;
import gengine.util.loaders.TiledWorldLoader;
import gengine.util.loaders.TilesetLoader;
import gengine.rendering.WorldRenderer;
import gengine.rendering.squaregrid.SquareGridRenderer;
import gengine.util.UnsupportedFormatException;
import gengine.util.coords.*;
import gengine.world.*;
import gengine.world.tile.Tileset;
import java.io.*;
import javax.swing.JFrame;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class EventyThingTest2 {

    public static void main(String[] args) throws WorldSizeException, IOException, UnsupportedFormatException, InterruptedException, DimMismatchException, ValueException {

        JFrame window = new JFrame();

        window.setTitle("Random Gamish Thing");
        window.setSize(1024, 768);

        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tileset ts = TilesetLoader.load(new File("/home/rkutina/testimages/world/imglist.txt"));

        TiledWorld w = TiledWorldLoader.load(new File("/home/rkutina/testimages/world/mapa.csv"));
        w.setTileset(ts);

        Simulator sim = new Simulator(w, 50);

        WorldRenderer wren = new SquareGridRenderer(64);

        AbstEventGenerator[] evgens = {
            new ProximityEventGenerator(w, 10),
            new KeyboardEventGenerator(w, window, 50),
            new MouseEventGenerator(w, window, wren, 50)
        };

        Controller ctrl = new Controller(sim, w, window, wren, evgens);
        ControllerFacade cf = ctrl;

        for (int i = 0; i < 20; i++) {
            ScaredBall sb1 = new ScaredBall(cf);

            Coords3D c = new Coords3D(
                    (float) Math.random() * w.getWorldSize().getX(),
                    (float) Math.random() * w.getWorldSize().getY(),
                    (float) Math.random() * w.getWorldSize().getZ()
            );

            sb1.setPos(c);

            w.addEntity(sb1);
        }

        ThePlayer ply = new ThePlayer(cf);

        ply.setPos(new Coords3D(0, 0, 0));
        
        w.addEntity(ply);
//        
//        Sentry sentry = new Sentry(cf);
//        sentry.setPos(new Coords3D(15, 15, 0));
//        w.addEntity(sentry);
//        
//        SentryProjectile sp = new SentryProjectile(cf, null, null);
//        sp.setPos(new Coords3D(1,1,0));
//        sp.setVelocity(new Coords3D(0.01, 0.01, 0));
//        w.addEntity(sp);
        
        ctrl.start();
    }
}
