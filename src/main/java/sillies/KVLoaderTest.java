package sillies;

import gengine.util.loaders.KeyValueLoader;
import gengine.util.UnsupportedFormatException;
import java.io.*;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class KVLoaderTest {

    public static void main(String[] args) throws IOException, UnsupportedFormatException {
        new KVLoaderTest().test();
    }

    public void test() throws IOException, UnsupportedFormatException {
        
        InputStream is = new FileInputStream(new File("/home/rkutina/NetBeansProjects/gengine/src/main/java/sillies/kvfile.txt"));
    }
}
