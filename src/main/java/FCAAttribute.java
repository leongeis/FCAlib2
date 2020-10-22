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
 * Describes a single Attribute with the
 * corresponding {@link FCAObject}s.
 * @author Leon Geis
 */
public class FCAAttribute {

    /**
     * Name of the Attribute.
     */
    private String name;

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
    public FCAAttribute(){
        this.objects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
