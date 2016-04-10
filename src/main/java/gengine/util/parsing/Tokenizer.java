package gengine.util.parsing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A handy 'universal' tokenizer!
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class Tokenizer {

    private final ArrayList<Token> tokens;
    private final ArrayList<TokenInfo> tokenInfos;

    public Tokenizer() {
        this.tokens = new ArrayList<>();
        this.tokenInfos = new ArrayList<>();
    }

    public void clearTokens() {
        tokens.clear();
    }

    public Token[] getTokens() {
        return tokens.toArray(new Token[tokens.size()]);
    }

    public void addTokenInfo(String regex, int tokenID) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), tokenID));
    }

    public void tokenize(String str) throws ParserException {
        String s = str.trim() + " ";
        
        while (!s.equals(" ")) {
            boolean match = false;
            for (TokenInfo info : tokenInfos) {
                Matcher m = info.getPattern().matcher(s);
                if (m.find()) {
                    match = true;
                    String tok = m.group().trim();
                    s = m.replaceFirst("").trim() + " ";
                    tokens.add(new Token(info.getTokenID(), tok));
                    break;
                }
            }
            if (!match) {
                throw new ParserException("Unexpected character in input: " + s);
            }
        }
    }
}
