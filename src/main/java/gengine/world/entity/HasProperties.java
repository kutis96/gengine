package gengine.world.entity;

import gengine.util.properties.Property;
import java.util.List;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public interface HasProperties {
    
    public Property getProperty(String key);
    
    public void setProperty(String key, Property property);
    
    public List<Property> getPropertyList();
    
    public void initializeProperties();
}
