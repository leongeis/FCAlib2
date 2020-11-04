package fca;
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
 *
 * @author Leon Geis
 */

public class FCAFormalContext<O,A> /*extends AbstractContext<A,O,FCAObject<>>*/ {

    /**
     * Name of the Context.
     */
    private static int contextID = 0;

    /**
     * List of all Objects of the Context.
     */
    private List<O> contextObjects;

    /**
     * List of all Attributes of the Context.
     */
    private List<A> contextAttributes;

    /**
     * Constructor of the class creates two empty Lists.
     */
    public FCAFormalContext(){
        this.contextAttributes=new ArrayList<>();
        this.contextObjects=new ArrayList<>();
        //Increment ID
        contextID++;
    }

    /**
     * Adds an object to the context.
     * @param o fca.FCAObject
     */
    public void addFCAObject(O o){
        this.contextObjects.add(o);
    }

    /**
     * Adds an Attribute to the conext.
     * @param a fca.FCAAttribute
     */
    public void addFCAAttribute(A a){
        this.contextAttributes.add(a);
    }

    /**
     * Get all Objects of the context.
     * @return List of FCAObjects.
     */
    public List<O> getContextObjects(){
        return this.contextObjects;
    }

    /**
     * Get all Attributes of the context.
     * @return List of FCAAttributes.
     */
    public List<A> getContextAttributes(){
        return this.contextAttributes;
    }

}
