package gengine.util.parsing;

import java.util.regex.Pattern;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class TokenInfo {

    public TokenInfo(Pattern regex, int tokenID) {
        this.pattern = regex;
        this.tokenID = tokenID;
    }

    private final Pattern pattern;
    private final int tokenID;

    public Pattern getPattern() {
        return pattern;
    }

    public int getTokenID() {
        return tokenID;
    }
}
