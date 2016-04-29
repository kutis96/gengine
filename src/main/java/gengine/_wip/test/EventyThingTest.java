package gengine._wip.test;

import gengine.util.loaders.TiledWorldLoader;
import gengine.util.loaders.TilesetLoader;
import gengine.events.EventGenerator;
import gengine.logic.Controller;
import gengine.logic.Simulator;
import gengine.rendering.WorldRenderer;
import gengine.rendering.squaregrid.SquareGridRenderer;
import gengine.util.UnsupportedFormatException;
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
    public static void main(String[] args) throws WorldSizeException, IOException, UnsupportedFormatException, InterruptedException {
        
        JFrame window = new JFrame();
        
        window.setTitle("Random Gamish Thing");
        window.setSize(800, 600);
        window.setVisible(true);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Tileset ts = TilesetLoader.load(new File("/home/rkutina/testing/gengine/world.tset"));
        World w = TiledWorldLoader.load(new File("/home/rkutina/testing/gengine/world.wrld"), ts);
        
        WorldRenderer wren = new SquareGridRenderer(32);
        
        Simulator sim = new Simulator(w, 50);
        
        EventGenerator[] evgens = {};
        
        Controller c = new Controller(sim, w, window, wren, evgens);
        
        c.start();
        
        Thread.sleep(15000);
        
        c.stop();
        System.exit(0);
    }
}
