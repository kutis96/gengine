package sillies;

import gengine.ctrls.Controller;
import gengine.ctrls.ControllerUpdater;
import gengine.rendering.*;
import gengine.rendering.squaregrid.SquareGridRenderer;
import gengine.util.WorldPack;
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
public class SquareTest2 {

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
                    (int) (rimg.getWidth() * scale),
                    (int) (rimg.getHeight() * scale),
                    BufferedImage.SCALE_FAST);

            this.img = new BufferedImage(ximg.getWidth(null), ximg.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            this.img.getGraphics().drawImage(ximg, 0, 0, null);

            this.seconds = Math.random() * 10 + 1;
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
        SquareTest2 it = new SquareTest2();
    }

    public SquareTest2() throws IOException, WorldSizeException, DimMismatchException {

        TestyWindow jgw;

        jgw = new TestyWindow();
        jgw.setSize(10 * 64, 10 * 64);

        TiledWorld tw;

        //create a tileset
        Tileset ts = new Tileset();

        int white = ts.addTile(new ImageTile(ImageIO.read(new File("/home/rkutina/testimages/white.png"))));
        int black = ts.addTile(new ImageTile(ImageIO.read(new File("/home/rkutina/testimages/black.png"))));
        //

        //create a ball entity for no particular reason
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

        ball.setPos(new Coords3D(1, 1, 0));

        tw = new TiledWorld(new Coords3D(8, 8, 1), ts);

        tw.addEntity(ball);

        for (int i = 0; i < 25; i++) {
            tw.addEntity(new CirclingBall());
        }

        //fill the whole world with black and white tiles
        for (int x = 0; x < tw.getWorldSize().getX(); x++) {
            for (int y = 0; y < tw.getWorldSize().getY(); y++) {
                int tileid = (((x ^ y) % 2) == 1) ? black : white;

                tw.setWorldtile(tileid, new Coords3D(x, y, 0));
            }
        }
        //done filling the world with tiles

        jgw.setTitle("Square Grid Rendering Test");
        jgw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GWindow gw = new GWindow();

        gw.setIgnoreRepaint(true);

        jgw.add(gw);
        jgw.setVisible(true);
        
        Controller ctrl = new Controller(gw, new WorldPack(tw, new SquareGridRenderer(64), null));

        Thread upt = new Thread(new ControllerUpdater(ctrl, 8));

        gw.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

                Coords3D c = ctrl.mouseToWorldCoords(e.getPoint());
                
                System.out.println("\tx:" + c.getX() + "\ty:" + c.getY());
                
                try {
                    ball.setPos(c);
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
}
