package api;

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

}
