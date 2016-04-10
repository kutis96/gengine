package gengine.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple loader for a very simple key:value file format.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class KeyValueLoader {

    public KeyValueLoader() {}

    private static final Logger LOG = Logger.getLogger(KeyValueLoader.class.getName());

    /**
     * Simplified loader for directly getting a HashMap out of a K:V file.
     *
     * @param is Input stream to load the data from.
     *
     * @return a new HashMap with the data.
     *
     * @throws IOException thrown on InputStream IOException. it is also thrown
     *                     when any of the lines is found not to match the
     *                     specified pattern.
     */
    public static Map<String, String> load(InputStream is) throws IOException {
        Map<String, String> data = new HashMap<>();

        load(is, data, ":", true);

        return data;
    }

    /**
     * Loads in a HashMap stored in the key : value format. This call gives the
     * user a bit more control of the various parameters.
     *
     * @param is             Input stream to load the data from.
     * @param data           A String:String hashmap to load data into.
     * @param separator      Separator string/character to use.
     * @param dieOnOverwrite When true, an exception will be thrown whenever a
     *                       key in the hashmap is being overwritten with
     *                       something else. When false, a warning would be
     *                       logged anyway.
     *
     * @throws IOException          thrown on InputStream IOException or when an
     *                              unsupported format is detected.
     * @throws NullPointerException thrown when the InputStream happens to be
     *                              null, separator happens to be null, the
     *                              hashmap just so happens to be null.
     *                              Basically happens when anything goes a bit
     *                              nuts. You know the drill.
     */
    public static void load(InputStream is, Map<String, String> data, String separator, boolean dieOnOverwrite) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        LOG.log(Level.FINEST, "Loading some K:V");

        String line;
        int linenum = 1;

        while ((line = br.readLine()) != null) {

            line = line.replaceAll("#.+", "").trim();  //comment stripping - all comments start with a hash symbol.

            if ("".equals(line)) {
                continue;           //empty lines will be ignored
            }

            //I'm using this simple checking instead of a full-blown regex or something,
            //  simply because this format is a bit far too simple to go to town on
            int seppos = line.indexOf(separator);   //separator position

            if (seppos == -1 || seppos != line.lastIndexOf(separator)) {
                throw new IOException("Unrecognized format on line " + linenum + "\n" + line);
            }

            String key, val;

            //key is anything before the separator
            key = line.substring(0, seppos).trim();

            //value is anything after the separator
            val = (seppos == line.length() - 1) ? "" : line.substring(seppos + 1).trim();

            //log the key/value and line for debug purposes
            LOG.log(Level.FINEST, "{0}: {1}\n\tk: {2}\n\tv: {3}\n", new Object[]{linenum, line, key, val});

            if (data.containsKey(key)) {
                String message = "Found a record with the same key on line " + linenum;

                LOG.log(Level.WARNING, message);

                if (dieOnOverwrite) {
                    throw new IOException(message);
                }
            }

            data.put(key, val);

            linenum++;
        }

        LOG.log(Level.FINEST, "Done.");
    }
}
