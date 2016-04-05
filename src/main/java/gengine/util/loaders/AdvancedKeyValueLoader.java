package gengine.util.loaders;

import gengine.util.UnsupportedFormatException;
import gengine.util.parsing.*;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loader able to load up various stuff from files.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class AdvancedKeyValueLoader {

    private static final Logger LOG = Logger.getLogger(AdvancedKeyValueLoader.class.getName());

    private HashMap<String, Object> data;

    private Tokenizer tok;

    private enum TokenEnum {

        KEY("[^:]+:"),
        SPACE("\\s+"),
        NEWLINE("(\\n+|\\r+)"),
        ARRAY_BEGIN("\\["),
        ARRAY_END("\\]"),
        ARRAY_SEPARATOR(","),
        STRING("\\\"[^\\\"]+\\\""),
        INTEGER("[+-]?[0-9]+"),
        FLOAT("[+-]?[0-9]+.([0-9]+)?");

        private String pat;

        TokenEnum(String pat) {
            this.pat = pat;
        }

        public String getPattern() {
            return this.pat;
        }
    }

    public AdvancedKeyValueLoader() {
        this.data = new HashMap<>();

        this.tok = new Tokenizer();

        for (TokenEnum t : TokenEnum.values()) {
            tok.addTokenInfo(t.getPattern(), t.ordinal());
        }

    }

    public void preload(InputStream is, boolean allowOverwrite) throws IOException, UnsupportedFormatException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        int linenum = 1;

        while ((line = br.readLine()) != null) {
            try {
                this.tok.tokenize(line);
            } catch (ParserException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new UnsupportedFormatException("Unknown token found at line " + linenum);
            }
        }
    }

    public void parse() throws ParserException {
        //TODO: actual parsing, can't be bothered right now.
    }

    private Object parseObject(String s) throws ParserException {
        this.tok.tokenize(s);

        Token[] ts = this.tok.getTokens();

        for (Token t : ts) {
            System.out.println(t.toString());
        }

        this.tok.clearTokens();

        return null;
    }

    public static void main(String[] args) throws ParserException {
        AdvancedKeyValueLoader a = new AdvancedKeyValueLoader();

        a.tok.tokenize("one: [ \"this is a string\" \n, \"in\", \"an\", \"array\" , 2123 ]");
        a.tok.tokenize("two: 1223");

        a.parse();

    }

}
