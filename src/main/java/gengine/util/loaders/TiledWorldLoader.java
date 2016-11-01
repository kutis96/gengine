package gengine.util.loaders;

import gengine.util.UnsupportedFormatException;
import gengine.util.neco.Neco3D;
import gengine.world.TiledWorld;
import gengine.world.WorldSizeException;
import java.io.*;
import java.util.Arrays;
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
     *
     * @param in InputStream to load a TiledWorld from.
     *
     * @return new TiledWorld
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws UnsupportedFormatException
     */
    public static TiledWorld load(InputStream in) throws FileNotFoundException, IOException, UnsupportedFormatException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

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
                    } catch (Exception ex) {
                        LOG.log(Level.SEVERE, null, ex);
                        throw new UnsupportedFormatException("Integers are required, found '" + nextbit + "' on line " + linecounter + "\n" + line);
                    }

                    vcollumn.add(bit);

                    if (nextCollumnSep <= nextSep) {
                        //it is the final bit in the current column
                        //push the current collumn onto the current row of columns.
                        if (!vcollumn.isEmpty()) {
                            row.add(vcollumn.toArray(new Integer[vcollumn.size()]));
                        }

                        //clear the current stack, so one could push new stuff in there
                        vcollumn.clear();

                    }
                }

                line = (ix == line.length()) ? "" : line.substring(ix + 1);
            }

            //we're done with this line
            if (!row.isEmpty()) {
                map.add(row.toArray(new Integer[vcollumn.size()][]));
            }
            row.clear();
        }

        LOG.log(Level.FINE, "Conversion complete.");

        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;

        Integer[][][] m = map.toArray(new Integer[map.size()][][]);
        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                for (int z = 0; z < m[y][x].length; z++) {
                    maxX = (x > maxX) ? x : maxX;
                    maxY = (y > maxY) ? y : maxY;
                    maxZ = (z > maxZ) ? z : maxZ;
                }
            }
        }

        int[] worldDimensions = new int[]{maxX + 1, maxY + 1, maxZ + 1};

        LOG.log(Level.FINE, "World dimensions assesed: {0}", Arrays.toString(worldDimensions));

        TiledWorld world;
        try {
            world = new TiledWorld(worldDimensions);
        } catch (WorldSizeException ex) {
            LOG.log(Level.SEVERE, "Unsupported world size: " + Arrays.toString(worldDimensions), ex);
            throw new UnsupportedFormatException("Unsupported world size: " + worldDimensions.toString() + "\n" + ex.getMessage());
        }

        for (int x = 0; x < m.length; x++) {
            for (int y = 0; y < m[x].length; y++) {
                for (int z = 0; z < m[y][x].length; z++) {
                    world.setWorldtile(m[y][x][z], new Neco3D(new int[]{x, y, z}, true));
                }
            }
        }

        LOG.fine("And we're done here. World loaded.");

        return world;
    }
}
