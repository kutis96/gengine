package sillies;

import gengine.world.World;
import gengine.world.tile.tiles.SillyIsometricTile;
import gengine.world.tile.tiles.AnimatedTile;
import gengine.world.tile.tiles.ImageTile;
import gengine.world.TiledWorld;
import gengine.util.coords.Coords3D;
import gengine.anim.AnimFrame;
import gengine.anim.AnimatedImage;
import gengine.rendering.*;
import gengine.rendering.isometric.IsometricRenderer;
import gengine.winman.GWindow;
import gengine.world.*;
import gengine.world.tile.Tileset;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class IsomTest2 {

    private static final Logger LOG = Logger.getLogger(IsomTest2.class.getName());

    public static void main(String[] args) throws IOException, WorldSizeException {
        IsomTest2 it = new IsomTest2();
    }

    GWindow gw;

    public IsomTest2() throws IOException, WorldSizeException {

        TestyWindow jgw;

        jgw = new TestyWindow();
        jgw.setSize(800, 350);

        TiledWorld tw;

        tw = new TiledWorld(new Coords3D(5, 5, 5), new Tileset());

        System.out.println("Worldsize: " + tw.getWorldSize().toString());

        for (int x = 0; x < tw.getWorldSize().getX(); x++) {
            for (int y = 0; y < tw.getWorldSize().getY(); y++) {

                tw.setWorldtile(
                        new ImageTile(ImageIO.read(new File("sand.png"))),
                        new Coords3D(x, y, 0)
                );
            }
        }

        Image snow = ImageIO.read(new File("snow.png"));
        Image stone = ImageIO.read(new File("stone.png"));
        Image tile = ImageIO.read(new File("tile.png"));
        Image sand = ImageIO.read(new File("sand.png"));

        try {
            tw.setWorldtile(new ImageTile(snow), new Coords3D(0, 0, 0));
            tw.setWorldtile(new ImageTile(stone), new Coords3D(0, 2, 0));

            tw.setWorldtile(new ImageTile(tile), new Coords3D(0, 3, 0));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        tw.setWorldtile(new SillyIsometricTile(Color.YELLOW), new Coords3D(3, 3, 1));

        tw.setWorldtile(new SillyIsometricTile(Color.RED), new Coords3D(1, 0, 2));
        tw.setWorldtile(new SillyIsometricTile(Color.RED), new Coords3D(2, 0, 2));
        tw.setWorldtile(new SillyIsometricTile(Color.RED), new Coords3D(3, 0, 2));

        tw.setWorldtile(new SillyIsometricTile(Color.BLUE), new Coords3D(1, 0, 1));

        tw.setWorldtile(new SillyIsometricTile(Color.BLUE), new Coords3D(1, 1, 2));
        tw.setWorldtile(new SillyIsometricTile(Color.BLUE), new Coords3D(1, 2, 3));
        tw.setWorldtile(new SillyIsometricTile(Color.BLUE), new Coords3D(1, 3, 4));

        tw.setWorldtile(new AnimatedTile(new AnimatedImage(new AnimFrame[]{
            new AnimFrame(tile, 500),
            new AnimFrame(snow, 200),
            new AnimFrame(sand, 100),
            new AnimFrame(snow, 200),})), new Coords3D(3, 3, 0));

        jgw.setTitle("Isometric Rendering Test");
        jgw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gw = new GWindow();

        gw.setIgnoreRepaint(true);

        jgw.add(gw);

        jgw.setVisible(true);

        Updater upd = new Updater(tw, gw);

        Thread upt = new Thread(upd);

        upt.start();
    }

    private class Updater implements Runnable {

        private volatile World w;
        private volatile GWindow gw;

        private volatile boolean running;

        private long lastmillis = -1;

        volatile BufferedImage bi;

        public Updater(World w, GWindow gw) {
            this.running = true;
            this.w = w;
            this.gw = gw;
        }

        double ups = 0;
        volatile int nev = 0;

        @Override
        public void run() {
            while (running) {
                if (lastmillis == -1) {
                    lastmillis = System.currentTimeMillis();
                }

                //delta tee in millis
                long dt = (System.currentTimeMillis() - lastmillis);

                long sleept = Math.max(20 - dt, 1);

                updateWorld(dt);
                try {
                    renderWorld();
                } catch (WorldTypeMismatchException | RendererException ex) {
                    Logger.getLogger(IsomTest2.class.getName()).log(Level.SEVERE, null, ex);
                    this.running = false;
                }
                paintScreen();

                ups = (ups + (1000 / sleept)) / 2;

                lastmillis = System.currentTimeMillis();

                if (nev % 50 == 0) {
                    System.out.println("UPS: " + (int) ups);
                }
                nev++;

                try {
                    Thread.sleep(sleept);
                } catch (InterruptedException ex) {
                }
            }
        }

        private void updateWorld(long dt) {
            w.tick(dt);
        }

        private void renderWorld() throws WorldTypeMismatchException, RendererException {
            bi = new BufferedImage(this.gw.getWidth(), this.gw.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

            IsometricRenderer ir = new IsometricRenderer(50);

            WorldRendererOptions ro = new WorldRendererOptions();

            ro.zoom = gw.getWidth() / 300.0;

            ir.render((TiledWorld) w, bi, ro);
        }

        private void paintScreen() {
            Graphics g;

            try {
                g = gw.getGraphics();

                if (g != null && bi != null) {
                    g.drawImage(bi, 0, 0, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            this.running = false;
        }
    }
}
