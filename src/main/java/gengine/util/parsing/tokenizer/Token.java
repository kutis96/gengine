package gengine.util.parsing.tokenizer;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Token {

    private final int tokenID;
    private final String sequence;

    public Token(int tokenID, String seq) {
        this.tokenID = tokenID;
        this.sequence = seq;
    }

    public int getTokenID() {
        return tokenID;
    }

    public String getSequence() {
        return sequence;
    }
}
