package gengine._wip.test;

import gengine.util.coords.Coords3D;
import gengine.util.coords.ValueException;
import gengine.world.TiledWorld;
import gengine.world.WorldSizeException;
import java.beans.XMLEncoder;
import java.io.*;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class PropTest implements Serializable {

    public static void main(String[] args) throws FileNotFoundException, IOException, ValueException, WorldSizeException {
        XMLEncoder xe;
        xe = new XMLEncoder(new FileOutputStream("/home/rkutina/testing/sertest.xml"));
        
        xe.writeObject(new Coords3D(2,5, (float) -23.4));
        
        xe.writeObject(new TiledWorld(new Coords3D(5, 7, 9)));
        
        xe.close();
    }
    
    public int a = 1;
    public double b = 0.2;
    public String s = "awesome";
}
