/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */


import java.util.ArrayList;
import java.util.List;

/**
 * Describes a single Formal Context with
 * the corresponding Objects and Attributes.
 * @author Leon Geis
 * TODO:INCIDENCE RELATION ?
 */

public class FormalContext {

    /**
     * Name of the Context.
     */
    private String contextName;

    /**
     * List of all Objects of the Context.
     */
    private List<FCAObject> contextObjects;

    /**
     * List of all Attributes of the Context.
     */
    private List<FCAAttribute> contextAttributes;

    /**
     * Constructor of the class creates two empty Lists.
     */
    public FormalContext(){
        this.contextAttributes=new ArrayList<>();
        this.contextObjects=new ArrayList<>();
    }

    /**
     * Adds an object to the context.
     * @param o FCAObject
     */
    public void addObject(FCAObject o){
        this.contextObjects.add(o);
    }

    /**
     * Adds an Attribute to the conext.
     * @param a FCAAttribute
     */
    public void addAttribute(FCAAttribute a){
        this.contextAttributes.add(a);
    }

    /**
     * Get all Objects of the context.
     * @return List of FCAObjects.
     */
    public List<FCAObject> getContextObjects(){
        return this.contextObjects;
    }

    /**
     * Get all Attributes of the context.
     * @return List of FCAAttributes.
     */
    public List<FCAAttribute> getContextAttributes(){
        return this.contextAttributes;
    }

}
