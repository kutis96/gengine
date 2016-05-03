package gengine.util.parsing;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
@SuppressWarnings("serial")
public class ParserException extends Exception {
    public ParserException(){
        super();
    }
    public ParserException(String msg){
        super(msg);
    }
}
