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
 * The first type parameter describes the type
 * of the objects of the context and the second of
 * the corresponding attributes.
 *
 * @author Leon Geis
 */

public class FCAFormalContext<O,A> {

    /**
     * ID of the Context.
     */
    private static int contextID = 0;

    /**
     * Expert of the Context.
     */
    private FCAExpert<O,A> expert;

    /**
     * List of all Objects of the Context.
     */
    private List<FCAObject<O,A>> contextObjects;

    /**
     * List of all Attributes of the Context.
     */
    private List<FCAAttribute<O,A>> contextAttributes;

    /**
     * Constructor of the class creates two empty Lists
     * for the Attributes and the Objects and initializes the
     * FormalContext Object for computation purposes, as well
     * as an Expert Object.
     */
    public FCAFormalContext(){
        this.contextAttributes=new ArrayList<>();
        this.contextObjects=new ArrayList<>();
        //this.expert = new FCAExpert();
        //Increment ID
        contextID++;
    }

    /**
     * Adds an object to the context.
     * @param o fca.FCAObject
     */
    public void addFCAObject(O o){
        this.contextObjects.add(new fca.FCAObject<>(o));
    }

    /**
     * Adds an Attribute to the context.
     * @param a fca.FCAAttribute
     */
    public void addFCAAttribute(A a){
        this.contextAttributes.add(new fca.FCAAttribute<>(a));
    }

    /**
     * Get all Objects of the context.
     * @return List of FCAObjects.
     */
    public List<fca.FCAObject<O,A>> getContextObjects(){
        return this.contextObjects;
    }

    /**
     * Get all Attributes of the context.
     * @return List of FCAAttributes.
     */
    public List<fca.FCAAttribute<O,A>> getContextAttributes(){
        return this.contextAttributes;
    }

    public void computeStemBase(){

    }

    public void nextClosure(){

    }

}
