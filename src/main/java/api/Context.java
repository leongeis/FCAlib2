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
     * @param <T> ObjectAPI Interface or any subtype.
     * @return List of all Objects, corresponding to the List
     *          of IDs.
     */
    <T extends ObjectAPI<O,A>> List<T> getObjects(List<O> IDs);

    /**
     * Get all Attributes, which correspond to the List of IDs.
     * @param IDs List of IDs
     * @param <T> Attribute Interface or any subtype.
     * @return List of all Attributes, corresponding to the List
     *          of IDs.
     */
    <T extends Attribute<O,A>> List<T> getAttributes(List<A> IDs);

    /**
     * Compute the Prime of a List of FCAAttributes.
     * @param attributes List of FCAAttributes.
     * @param a Object used due to type erasure
     * @return List of FCAObjects all of the FCAAttributes
     * have in common.
     */
    List<FCAObject<O,A>> computePrime (List<FCAAttribute<O,A>> attributes, Attribute<O,A> a);
    /**
     * Compute the Prime of a List of FCAObjects.
     * @param objects List of FCAObjects.
     * @param o Object used due to type erasure
     * @return List of FCAAttributes all of the FCAObjects
     * have in common.
     */
    List<FCAAttribute<O,A>> computePrime (List<FCAObject<O,A>> objects, ObjectAPI<O,A> o);

}
