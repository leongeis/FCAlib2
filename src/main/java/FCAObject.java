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
 * Describes a single Object with the
 * corresponding {@link FCAAttribute}s.
 * @author Leon Geis
 */
public class FCAObject {

    /**
     * Name of the Object.
     */
    private String objectName;

    /**
     * List of Attributes an Object has.
     * Can be seen as a "x" in the given context
     * for that object.
     */
    private List<FCAAttribute> attributes;

    /**
     * Constructor of the class. Creates a new and empty
     * List of {@link FCAAttribute}s.
     */
    public FCAObject(){
        this.attributes = new ArrayList<>();
    }

    /**
     * @return ObjectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName String of the Name.
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<FCAAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Add an Attribute to the Object.
     * @param attribute Attribute that has to be added.
     */
    public void addAttribute(FCAAttribute attribute){
        this.attributes.add(attribute);
    }
}
