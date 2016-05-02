package gengine.util.loaders;

import gengine.util.KeyValLoader;
import gengine.util.UnsupportedFormatException;
import gengine.world.tile.*;
import gengine.world.tile.tiles.AnimatedTile;
import gengine.world.tile.tiles.ImageTile;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

/**
 * Util class containing some Tileset loaders.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TilesetLoader {

    public TilesetLoader() {

    }

    /**
     * Load a tileset from a given K:V file.
     *
     * @param f File to load from
     *
     * @return new Tileset
     *
     * @throws IOException
     * @throws UnsupportedFormatException thrown when the K:V loader thinks the
     *                                    file's format is either unacceptable,
     *                                    or totally cursed.
     */
    public static Tileset load(File f) throws IOException, UnsupportedFormatException {

        //load Key:Value values from the given file
        Map<String, String> kv = KeyValLoader.load(new FileInputStream(f));

        Tileset tileset = new Tileset();

        for (Entry<String, String> e : kv.entrySet()) {
            String kStr = e.getKey();
            String vStr = e.getValue();

            //Tileset keys are only supposed to be integers for some reason
            //  (mostly because I'm apparently one heck of a lazy bastard)
            if (!kStr.matches("[0-9]+")) {
                throw new UnsupportedFormatException("Tileset keys are only meant to be integers in this version!\nFound a non-integer one saying '" + kStr + "'");
            }

            int index = Integer.parseInt(kStr);

            File tilefile = new File(f.getParent() + "/" + vStr);

            if (!tilefile.exists()) {
                throw new IOException("File '" + tilefile.getCanonicalPath() + "' was not found. (" + vStr + ")");
            }

            Tile tile;

            if (tilefile.isDirectory()) {
                //load it as an animated tile
                tile = loadAnimTile(tilefile);
            } else if (tilefile.isFile()) {
                //load it as a regular image tile
                tile = loadImgTile(tilefile);
            } else {
                //what the hell?
                throw new IOException("I have no idea how on earth did this happen, but if you read this, it has probably happened. Your computer is cursed. Good luck.");
            }

            try {
                tileset.setTileWithId(index, tile);
            } catch (TilesetIndexException ex) {
                throw new UnsupportedFormatException("Tileset index fudgeup.\n" + ex.getMessage());
            }

        }

        return tileset;
    }

    /**
     * Creates an ImageTile from a specified File, which is indeed a file.
     *
     * @param f File containing the image.
     *
     * @return ImageTile with the specified Image.
     *
     * @throws IOException
     */
    private static ImageTile loadImgTile(File f) throws IOException {
        BufferedImage img = ImageIO.read(f);

        return new ImageTile(img);
    }

    private static AnimatedTile loadAnimTile(File f) {
        //TODO: make an animated tile loader
        // it should basically open a config file in a directory
        // figure out the delays for each frame
        // and create an animated thing out of them all.
        // if the config file isn't there, use some mildly reasonable default delay thing.
        throw new UnsupportedOperationException("I'll have to finish this one later :|");

    }

}
