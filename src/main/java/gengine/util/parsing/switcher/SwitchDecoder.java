package gengine.util.parsing.switcher;

import gengine.util.parsing.ParserException;
import java.util.LinkedList;
import java.util.List;

/**
 * A switch decoder. To be used for decoding command line arguments etc. I know
 * it's a bit of an overkill, but ehh. I can still use it for something else
 * later on.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SwitchDecoder {

    public static SwitchToken[] decode(String[] input, SwitchTemplate[] rulebook) throws ParserException {
        List<SwitchToken> results = new LinkedList<>();
        List<String> buf = new LinkedList<>();

        SwitchTemplate current = new SwitchTemplate("", 0);
        String matchystring = "";

        //TODO: prettify the exceptions to make them basically directly slappable to the terminal
        for (int ix = 0; ix <= input.length; ix++) {
            SwitchTemplate st = null;

            if (ix != input.length && (null == (st = match(input[ix], rulebook)))) {
                if (buf.size() == current.maxparams) {
                    throw new ParserException("Too many parameters found for rule " + current.toString() + " when parsing " + arrayToString(input) + " on index " + ix);
                }

                //continue adding data to the last one
                buf.add(input[ix]);
            } else {
                //check whether it satisfies the minimum args requirement
                if (buf.size() < current.minparams) {
                    throw new ParserException("Too few parameters found for rule " + current.toString() + " when parsing " + arrayToString(input) + " on index " + ix);
                }

                if (ix != 0) {
                    //create a new SwitchToken here
                    results.add(new SwitchToken(current, matchystring, buf.toArray(new String[buf.size()])));
                    buf.clear();
                }
                
                if (ix < input.length) {
                    //new current template!
                    matchystring = input[ix];
                    current = st;
                }
            }
        }

        return results.toArray(new SwitchToken[results.size()]);
    }

    private static SwitchTemplate match(String s, SwitchTemplate[] rulebook) {
        for (SwitchTemplate rule : rulebook) {
            if (s.matches(rule.template)) { //actually a regex
                return rule;
            }
        }
        return null;
    }

    private static String arrayToString(String[] a) {
        String r = "";
        for (String s : a) {
            r += s + " ";
        }
        return r;
    }
}
