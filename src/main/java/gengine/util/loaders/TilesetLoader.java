package gengine.util.loaders;

import gengine.anim.SimpleAnimatedImage;
import gengine.util.*;
import gengine.util.parsing.ParserException;
import gengine.util.parsing.switcher.*;
import gengine.world.tile.*;
import gengine.world.tile.tiles.AnimatedTile;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Util class containing some Tileset loaders.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TilesetLoader {

    private static final Logger LOG = Logger.getLogger(TilesetLoader.class.getName());

    public TilesetLoader() {

    }

    /**
     * Load a tileset from a given K:V file.
     *
     * @param fis InputStream of the tileset K:V file
     * @param base Base directory of all the tiles
     * @param asResource true when loading resources, false when loading files
     *
     * @return new Tileset
     *
     * @throws IOException
     * @throws UnsupportedFormatException thrown when the K:V loader thinks the
     * file's format is either unacceptable, or totally cursed.
     */
    public static Tileset load(InputStream fis, String base, boolean asResource) throws IOException, UnsupportedFormatException {

        //load Key:Value values from the given file
        Map<String, String[]> kv = ExtendedKeyValLoader.load(fis);

        Tileset tileset = new Tileset();

        for (Entry<String, String[]> e : kv.entrySet()) {
            String kStr = e.getKey();
            String[] vStrA = e.getValue();

            //Tileset keys are only supposed to be integers for some reason
            //  (mostly because I'm apparently one heck of a lazy bastard)
            if (!kStr.matches("[0-9]+")) {
                throw new UnsupportedFormatException("Tileset keys are only meant to be integers in this version!\nFound a non-integer one saying '" + kStr + "'");
            }

            int index = Integer.parseInt(kStr);

            Tile tile;

            try {
                if (asResource) {
                    tile = loadAnimTile(TilesetLoader.class.getResourceAsStream(base + "/" + vStrA[0]), vStrA);
                } else {
                    tile = loadAnimTile(new FileInputStream(base + "/" + vStrA[0]), vStrA);
                }

                tileset.setTileWithId(index, tile);
            } catch (TilesetIndexException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new UnsupportedFormatException("Tileset index fudgeup.\n" + ex.getMessage());
            } catch (ParserException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new UnsupportedFormatException("Found a parser issue parsing '" + kStr + "'\n" + ex.getMessage());
            }

        }

        return tileset;
    }

    private static AnimatedTile loadAnimTile(InputStream fis, String[] options) throws IOException, ParserException {

        SwitchTemplate tempWall = new SwitchTemplate("(?i)w(all)?");
        SwitchTemplate tempNumber = new SwitchTemplate("(-)?[1-9]([0-9]+)?");
        SwitchTemplate tempAny = new SwitchTemplate(".+");

        SwitchTemplate[] rulebook = {
            tempWall, tempNumber, tempAny
        };

        SwitchToken[] aDecTok = SwitchDecoder.decode(options, rulebook);

        boolean isWall = false;
        int intParams[] = {-1, -1, 100};   //default values for Xsize, Ysize, animPeriod
        int sx = 0;
        int ac = 0;

        for (SwitchToken st : aDecTok) {
            if (tempWall.equals(st.getTemplate())) {
                if (isWall) {
                    LOG.warning("Found the same tag twice!");
                }
                isWall = true;
                continue;
            }
            if (tempNumber.equals(st.getTemplate())) {
                int n = Integer.parseInt(st.getString());
                if (sx < intParams.length) {
                    intParams[sx] = n;
                    sx++;
                } else {
                    LOG.warning("Found too many integer params in here");
                }
                continue;
            }
            if (tempAny.equals(st.getTemplate())) {
                ac++;
                if (ac > 1) {
                    LOG.warning("Found too many string params in here");
                }
            }
        }

        int xsize = intParams[0];   //X size
        int ysize = intParams[1];   //Y size
        int aperi = intParams[2];   //anim. period

        BufferedImage img = ImageIO.read(fis);

        if (xsize == -1 && ysize == -1) {
            //determine the tilesize from an image, assuming square tiles.

            xsize = Math.min(img.getHeight(), img.getWidth());
        }

        if (ysize == -1) {
            //only one parameter was supplied, assume a square
            ysize = xsize;
        }

        List<BufferedImage> images = new LinkedList<>();

        LOG.log(Level.INFO, "Loading new AnimatedTile. Assumed size: {0}, {1}. Assumed period: {2}ms ({3}Hz). It {5} a wall.", new Object[]{xsize, ysize, aperi, 1000. / aperi, (isWall) ? "is" : "is not"});

        for (int x = 0; x < img.getWidth(); x += xsize) {

            for (int y = 0; y < img.getHeight(); y += ysize) {

                BufferedImage i = img.getSubimage(x, y, xsize, ysize);

                images.add(i);
            }

        }

        AnimatedTile ret = new AnimatedTile(
                new SimpleAnimatedImage(
                        images.toArray(new BufferedImage[images.size()]),
                        aperi
                ));

        ret.setWall(isWall);

        //TODO: move this to some test
        if (ret.isWall() != isWall) {
            throw new RuntimeException("Wallness setter is broken!");
        }

        return ret;

    }

}
