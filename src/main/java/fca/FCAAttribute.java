package fca;/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a single Attribute with the
 * corresponding {@link FCAObject}s.
 * @author Leon Geis
 */
public class FCAAttribute {

    /**
     * Name of the Attribute.
     */
    private String attributeName;

    /**
     * List of Objects an Attribute has.
     * Can be seen as a "x" in the given context
     * for that Attribute.
     */
    private List<FCAObject> objects;

    /**
     * Constructor of the class, which creates a new
     * and empty List of {@link FCAObject}s.
     */
    public FCAAttribute(String name){
        this.objects = new ArrayList<>();
        this.attributeName=name;
    }

    /**
     * Get Name of the Attribute.
     * @return String of the name.
     */
    public String getName() {
        return this.attributeName;
    }

    /**
     * Set Name of the Attribute.
     * @param name String of the name.
     */
    public void setName(String name) {
        this.attributeName = name;
    }

    /**
     * Get all FCAObjects, which have this attribute.
     * @return List of FCAObjects.
     */
    public List<FCAObject> getObjects() {
        return objects;
    }

    /**
     * Adds an Object to the Attribute.
     * @param object Object that has to be added.
     */
    public void addObject(FCAObject object){
        this.objects.add(object);
    }
}
