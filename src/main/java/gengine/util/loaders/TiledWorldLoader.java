package gengine.util.loaders;

import gengine.util.UnsupportedFormatException;
import gengine.util.coords.Coords3D;
import gengine.world.TiledWorld;
import gengine.world.WorldSizeException;
import gengine.world.tile.Tileset;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loader designed for loading a TiledWorld from a specified file.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TiledWorldLoader {

    private static final Logger LOG = Logger.getLogger(TiledWorldLoader.class.getName());

    /**
     * Loads a new TiledWorld from the file specified.
     *
     * @param f
     * @param tileset
     *
     * @return
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws UnsupportedFormatException
     */
    public static TiledWorld load(File f, Tileset tileset) throws FileNotFoundException, IOException, UnsupportedFormatException {

        BufferedReader br = new BufferedReader(new FileReader(f));

        String line;

        int linecounter = 0;

        LinkedList<Integer[][]> map = new LinkedList<>();

        LOG.log(Level.FINE, "Loading a new TiledWorld from a file {0}", f.getAbsolutePath());

        while ((line = br.readLine()) != null) {
            linecounter++;

            line = line.replaceAll("#.+", "");   //strip comments
            line = line.replaceAll("\\s", "");   //remove spaces
            line += ";";
            line = line.replaceAll(";;", ";");

            LOG.log(Level.FINE, "\t{0}\t{1}", new Object[]{linecounter, line});

            int nextSep;    //next separator (,) index
            int nextCollumnSep; //next collumn separator (;) index

            LinkedList<Integer[]> row = new LinkedList<>();
            LinkedList<Integer> vcollumn = new LinkedList<>();

            while (!"".equals(line)) {

                nextCollumnSep = line.indexOf(';');
                nextSep = line.indexOf(',');

                int ix;

                if (nextCollumnSep < 0) {
                    break;
                }
                if (nextSep < 0) {
                    nextSep = nextCollumnSep;
                }

                ix = Math.min(nextSep, nextCollumnSep);

                String nextbit = line.substring(0, ix);
                int bit;

                if (!"".equals(nextbit)) {

                    try {
                        bit = Integer.parseInt(nextbit);
                    } catch (Exception e) {
                        System.out.println("ix: " + ix + "\tnc: " + nextCollumnSep + "\tns: " + nextSep);
                        throw new UnsupportedFormatException("Integers are required, found '" + nextbit + "' on line " + linecounter + " in " + f.getAbsolutePath() + "\n" + line);
                    }

                    vcollumn.add(bit);

                    if (nextCollumnSep <= nextSep) {
                        //it is the final bit in the current column
                        //push the current collumn onto the current row of columns.
                        if (vcollumn.size() != 0) {
                            row.add(vcollumn.toArray(new Integer[vcollumn.size()]));
                        }

                        //clear the current stack, so one could push new stuff in there
                        vcollumn.clear();

                    }
                }

                line = (ix == line.length()) ? "" : line.substring(ix + 1);
            }

            //we're done with this line
            if (row.size() != 0) {
                map.add(row.toArray(new Integer[vcollumn.size()][]));
            }
            row.clear();
        }

        LOG.log(Level.FINE, "Conversion complete.");

        int max_x = 0;
        int max_y = 0;
        int max_z = 0;

        Integer[][][] m = map.toArray(new Integer[map.size()][][]);
        for (int x = 0; x < m.length; x++) {
            for (int y = 0; y < m[x].length; y++) {
                for (int z = 0; z < m[x][y].length; z++) {
                    max_x = (x > max_x) ? x : max_x;
                    max_y = (y > max_y) ? y : max_y;
                    max_z = (z > max_z) ? z : max_z;
                }
            }
        }

        Coords3D worldDimensions = new Coords3D(max_x + 1, max_y + 1, max_z + 1);

        LOG.log(Level.FINE, "World dimensions assesed: {0}", worldDimensions.toString());

        TiledWorld world;
        try {
            world = new TiledWorld(worldDimensions, tileset);
        } catch (WorldSizeException ex) {
            throw new UnsupportedFormatException("Unsupported world size: " + worldDimensions.toString() + "\n" + ex.getMessage());
        }

        for (int x = 0; x < m.length; x++) {
            for (int y = 0; y < m[x].length; y++) {
                for (int z = 0; z < m[x][y].length; z++) {
                    world.setWorldtile(m[x][y][z], new Coords3D(x, y, z));
                }
            }
        }

        LOG.fine("And we're done here. World loaded.");

        return world;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, UnsupportedFormatException, WorldSizeException {
        TiledWorldLoader.load(new File("/home/rkutina/testing/gengine/world.wrld"), new Tileset());
    }
}
