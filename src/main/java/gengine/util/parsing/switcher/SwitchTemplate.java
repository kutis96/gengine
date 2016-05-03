package gengine.util.parsing.switcher;

/**
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class SwitchTemplate {
    public SwitchTemplate(String template){
        this(template, 0);
    }
    
    public SwitchTemplate(String template, int nparams){
        this(template, nparams, nparams);
    }
    
    public SwitchTemplate(String template, int minparams, int maxparams){
        this.maxparams = maxparams;
        this.minparams = minparams;
        this.template = template;
    }
    
    public final String template;
    public final int maxparams;
    public final int minparams;

    @Override
    public String toString() {
        return template + " (" + minparams + "," + maxparams + ")";
    }

}
