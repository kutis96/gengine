package gengine.util.properties;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Property<T> {
    
    public Property(){
        this.val = null;
    }
    
    public Property(T defaultvalue){
        this.val = defaultvalue;
    }
    
    private T val;
    
    /**
     * Gets the property value.
     * @return property value
     */
    public T getValue(){
        return this.val;
    }
    
    /**
     * Sets the property value.
     * @param value Value to set!
     */
    public void setValue(T value){
        this.val = value;
    }
}
