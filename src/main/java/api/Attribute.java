package api;

/**
 * Interface describing a single Attribute.
 * @param <O> Type of the Object this Attribute holds.
 * @param <A> Type of the Attribute itself.
 * @author Leon Geis
 */
public interface Attribute<O,A> extends ClosureOperator {

    /**
     * Get ID of the Attribute.
     * @return Attribute ID.
     */
    A getAttributeID();
    /**
     * Set Name of the Attribute.
     * @param id ID of the name.
     */
    void setAttributeID(A id);
    /**
     * Adds an Object to the Attribute.
     * @param object Object that has to be added.
     */
    void addObject(O object);

}
