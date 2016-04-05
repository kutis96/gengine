package sillies;

import gengine.rendering.*;
import gengine.rendering.squaregrid.SquareGridRenderer;
import gengine.util.coords.*;
import gengine.winman.GWindow;
import gengine.world.*;
import gengine.world.entity.WorldEntity;
import gengine.world.tile.tiles.ImageTile;
import gengine.world.tile.Tileset;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
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
public class SquareTest1 {

    private class CirclingBall extends WorldEntity {

        private final BufferedImage img;

        long total = 0;
        double amplitude;
        double seconds;
        double period;

        private CirclingBall() throws IOException {
            BufferedImage rimg = ImageIO.read(new File("/home/rkutina/testimages/blueball.png"));
            
            double scale = (Math.random() + 0.5);
            
            Image ximg = rimg.getScaledInstance(
                    (int)(rimg.getWidth()*scale),
                    (int)(rimg.getHeight()*scale),
                    BufferedImage.SCALE_SMOOTH);
            
            this.img = new BufferedImage(ximg.getWidth(null), ximg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            
            this.img.getGraphics().drawImage(ximg, 0, 0, null);
            
            this.seconds = Math.random()*10 + 1;
            this.period = 1000 * seconds / (2 * Math.PI);
            this.total = (long) (Math.random() * period * 4 * Math.PI);
            this.amplitude = Math.random() * 2.5 + 0.5;
        }

        @Override
        public void tick(World w, long dt) {
            this.total += dt;

            try {
                this.setPos(new Coords3D(
                        4 + (float) (amplitude * Math.sin((double) total / period)),
                        4 + (float) (amplitude * Math.cos((double) total / period)), 0));
            } catch (DimMismatchException ex) {
                Logger.getLogger(SquareTest1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public Image render() {
            return img;
        }
    }

    public static void main(String[] args) throws IOException, WorldSizeException, DimMismatchException {
        SquareTest1 it = new SquareTest1();
    }

    GWindow gw;

    public SquareTest1() throws IOException, WorldSizeException, DimMismatchException {

        TestyWindow jgw;

        jgw = new TestyWindow();
        jgw.setSize(8*64, 8*64);

        TiledWorld tw;

        Tileset ts = new Tileset();

        int white = ts.addTile(new ImageTile(ImageIO.read(new File("/home/rkutina/testimages/white.png"))));
        int black = ts.addTile(new ImageTile(ImageIO.read(new File("/home/rkutina/testimages/black.png"))));

        WorldEntity ball = new WorldEntity() {

            private final BufferedImage img = ImageIO.read(new File("/home/rkutina/testimages/blueball.png"));

            @Override
            public void tick(World w, long dt) {

            }

            @Override
            public Image render() {
                return img;
            }
        };

        ball.setPos(new Coords3D(3, 2, 0));

        tw = new TiledWorld(new Coords3D(8, 8, 1), ts);

        tw.addEntity(ball);

        for (int i = 0; i < 20; i++) {
            tw.addEntity(new CirclingBall());
        }

        for (int x = 0; x < tw.getWorldSize().getX(); x++) {
            for (int y = 0; y < tw.getWorldSize().getY(); y++) {
                int tileid = (((x ^ y) % 2) == 1) ? black : white;

                tw.setWorldtile(tileid, new Coords3D(x, y, 0));
            }
        }

        jgw.setTitle("Square Grid Rendering Test");
        jgw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gw = new GWindow();

        gw.setIgnoreRepaint(true);

        jgw.add(gw);

        jgw.setVisible(true);

        Updater upd = new Updater(tw, gw);

        Thread upt = new Thread(upd);

        gw.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                float x = ((float) e.getX() / gw.getWidth()) * tw.getWorldSize().getX();
                float y = ((float) e.getY() / gw.getHeight()) * tw.getWorldSize().getY();
                System.out.println("x:" + x + "\ty:" + y);
                try {
                    ball.setPos(new Coords3D(x, y, 0));
                } catch (DimMismatchException ex) {
                    Logger.getLogger(SquareTest1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

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
                    Logger.getLogger(SquareTest1.class.getName()).log(Level.SEVERE, null, ex);
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

            int tilesize = 64;
            
            SquareGridRenderer sqrgrdrndrr = new SquareGridRenderer(tilesize);

            WorldRendererOptions ro = new WorldRendererOptions();
            
            ro.zoom = Math.min(
                    w.getWorldSize().getX()*tilesize/gw.getWidth(),
                    w.getWorldSize().getY()*tilesize/gw.getHeight());

            sqrgrdrndrr.render(w, bi, ro);
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
