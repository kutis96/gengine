package gengine.util.parsing.switcher;

import gengine.util.parsing.ParserException;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SwitchDecoderTest {
    
    private static final String opt1 = "--fish";
    private static final String opt2 = "-i";
    private static final String opt3 = "-o";
    private static final String opt4 = "-a";    

    private static final SwitchTemplate[] rulebook = {
        new SwitchTemplate(opt1),
        new SwitchTemplate(opt2, 1),
        new SwitchTemplate(opt3, 1, Integer.MAX_VALUE),
        new SwitchTemplate(opt4, 2, 3)
    };

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public SwitchDecoderTest() {
        
    }

    @Test
    public void testDecode() throws ParserException {

        String[] a = {
            opt1,
            opt2,
            "fishy.txt",
            opt3,
            "file1.blob",
            "file2.blab",
            "file3.bleb",
            opt4,
            "11",
            "12",
            opt4,
            "21",
            "22",
            "23"
        };

        SwitchToken[] toks = SwitchDecoder.decode(a, rulebook);

        SwitchToken[] expected = {
            new SwitchToken(rulebook[0], opt1, new String[]{}),
            new SwitchToken(rulebook[1], opt2, new String[]{"fish.txt"}),
            new SwitchToken(rulebook[2], opt3, new String[]{"file1", "file2", "file3"}),
            new SwitchToken(rulebook[3], opt4, new String[]{"1", "2"}),
            new SwitchToken(rulebook[3], opt4, new String[]{"1", "2", "3"}),
        };

        //!!!
//        assertArrayEquals(expected, toks);
    }

    @Test
    public void testDecodeFails() throws ParserException {
        String[] a = {
            opt1,
            "aLonelyArgument",
            opt2,
            opt3,
            "file1",
            "file2",
            "file3"
        };
        
        String[] b = {
            opt4,
            "0"
        };
        
        String[] c = {
            opt4,
            "1",
            "2",
            "3",
            "4"
        };

        exception.expect(ParserException.class);
        SwitchDecoder.decode(a, rulebook);

        exception.expect(ParserException.class);
        SwitchDecoder.decode(b, rulebook);
        
        exception.expect(ParserException.class);
        SwitchDecoder.decode(c, rulebook);
        
    }
    
    @Test
    public void testEquals() {
        SwitchToken t1 = new SwitchToken(rulebook[0], opt1, new String[]{});
        SwitchToken t2 = new SwitchToken(rulebook[0], opt1, new String[]{});
        
        assertEquals(true, t1.equals(t2));
        assertEquals(true, t2.equals(t1));
        
        SwitchToken t3 = new SwitchToken(rulebook[0], "--fishy", new String[]{});
        assertEquals(false, t2.equals(t3));
        assertEquals(false, t3.equals(t2));
        
        SwitchToken t4 = new SwitchToken(rulebook[0], opt1, new String[]{""});
        assertEquals(false, t4.equals(t3));
        assertEquals(false, t4.equals(t2));
        assertEquals(true, t4.equals(t4));
        
        SwitchToken t5 = new SwitchToken(rulebook[0], "--fishy", new String[]{""});
        assertEquals(false, t5.equals(t4));
        assertEquals(false, t5.equals(t3));
        assertEquals(false, t5.equals(t2));
        assertEquals(false, t4.equals(t5));
        assertEquals(false, t3.equals(t5));
        assertEquals(false, t2.equals(t5));
        assertEquals(true, t5.equals(t5));
    }
}
