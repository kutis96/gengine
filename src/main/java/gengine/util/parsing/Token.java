package gengine.util.parsing;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Token {

    public Token(int tokenID, String seq) {
        this.tokenID = tokenID;
        this.sequence = seq;
    }

    private final int tokenID;
    private final String sequence;

    public int getTokenID() {
        return this.tokenID;
    }

    public String getSequence() {
        return this.sequence;
    }
    
    @Override
    public String toString(){
        return "ID : " + this.tokenID + "\nStr: " + this.sequence + "\n";
    }
}
