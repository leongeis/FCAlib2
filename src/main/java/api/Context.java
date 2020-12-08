package api;

import fca.FCAAttribute;
import fca.FCAObject;

import java.util.List;

/**
 * Interface describing a Context in FCA.
 * @author Leon Geis
 */
public interface Context<O,A> {

    /**
     * Get all Objects, which correspond to the List of IDs.
     * @param IDs List of IDs
     * @return List of all Objects, corresponding to the List
     *          of IDs.
     */
    List<ObjectAPI<O,A>> getObjects(List<O> IDs);

    /**
     * Get all Attributes, which correspond to the List of IDs.
     * @param IDs List of IDs
     * @param <T> Attribute Interface or any subtype.
     * @return List of all Attributes, corresponding to the List
     *          of IDs.
     */
    List<Attribute<O,A>> getAttributes(List<A> IDs);

    /**
     * Compute the Prime of a List of FCAAttributes.
     * @param attributes List of FCAAttributes.
     * @return List of FCAObjects all of the FCAAttributes
     * have in common.
     */
    <T extends ObjectAPI<O,A>> List<T> computePrimeOfAttributes(List<? extends Attribute<O,A>> attributes);

    /**
     * Compute the Prime of a List of FCAObjects.
     * @param objects List of FCAObjects.
     * @return List of FCAAttributes all of the FCAObjects
     * have in common.
     */
    <T extends Attribute<O,A>> List<T> computePrimeOfObjects(List<? extends ObjectAPI<O, A>> objects);

    public <T extends ObjectAPI<O,A>> void createObject(O o, Class<T> clazz);
    public void createFCAAttribute(A a);
    boolean containsAttribute(A a);
    boolean containsObject(O o);
    void addFCAObject(FCAObject<O,A> o);
    void addFCAAttribute(FCAAttribute<O,A> a);
    List<ObjectAPI<O,A>> getContextObjects();
    List<Attribute<O,A>> getContextAttributes();
    Attribute<O,A> getAttribute(A a);
    ObjectAPI<O,A> getObject(O o);



}
