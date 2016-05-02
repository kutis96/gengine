package gengine.util.loaders;

import gengine.util.UnsupportedFormatException;
import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
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

    //TODO: change this loader to load directly into an existing world, rather than having to supply it the tileset.
    /**
     * Loads a new TiledWorld from the file specified.
     *
     * @param file    File to load the world from
     * @param tileset Tileset to use
     *
     * @return new TiledWorld
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws UnsupportedFormatException
     */
    public static TiledWorld load(File file, Tileset tileset) throws FileNotFoundException, IOException, UnsupportedFormatException {

        BufferedReader br = new BufferedReader(new FileReader(file));

        LOG.log(Level.FINE, "Loading a new TiledWorld from a file {0}", file.getAbsolutePath());

        LinkedList<Integer[][]> map = new LinkedList<>();   //a 'map' to eventually contain all the tile IDs

        String line;    //line buffer
        int linecounter = 0;    //line counter, useful for readable exception throwing

        while ((line = br.readLine()) != null) {
            linecounter++;

            line = line.replaceAll("#.+", "");   //strip comments
            line = line.replaceAll("\\s", "");   //remove spaces
            line += ";";
            line = line.replaceAll(";;", ";");

            LOG.log(Level.FINE, "\t{0}\t{1}", new Object[]{linecounter, line});

            int nextSep;    //next separator (,) index
            int nextCollumnSep; //next collumn separator (;) index

            LinkedList<Integer[]> row = new LinkedList<>();     //row of tileIDs (X axis) containing all the vcollumns
            LinkedList<Integer> vcollumn = new LinkedList<>();  //a 'vertical' collumn. (Z axis)

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
                        throw new UnsupportedFormatException("Integers are required, found '" + nextbit + "' on line " + linecounter + " in " + file.getAbsolutePath() + "\n" + line);
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

        Coords3D worldDimensions;
        try {
            worldDimensions = new Coords3D(max_x + 1, max_y + 1, max_z + 1);
        } catch (ValueException ex) {
            LOG.severe("Found a ValueException where it definitely shoudln't have occured!");
            return null;
        }

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
                    try {
                        world.setWorldtile(m[x][y][z], new Coords3D(x, y, z));
                    } catch (ValueException ex) {
                        LOG.severe("Found a ValueException where it definitely shoudln't have occured!");
                    }
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
