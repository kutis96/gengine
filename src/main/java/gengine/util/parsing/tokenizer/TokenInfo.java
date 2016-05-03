package gengine.util.parsing.tokenizer;

import java.util.regex.Pattern;

/**
 * A container storing token-defining data - the pattern to match and the
 * numeric ID associated with it.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TokenInfo {

    private final Pattern pattern;                  
    private final int tokenID;

    public TokenInfo(Pattern regex, int tokenID) {
        this.pattern = regex;
        this.tokenID = tokenID;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getTokenID() {
        return tokenID;
    }
}
