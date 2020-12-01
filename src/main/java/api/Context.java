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
     * Returns all Attribute Objects, which correspond to the
     * List of IDs.
     * @param IDs List of IDs
     * @param a Object that matches the returned Attributes.
     * @return List of Attribute Objects
     */
    List<Attribute<O,A>> getEntities(List<A> IDs, Attribute<O,A> a);
    /**
     * Returns all Objects, which correspond to the
     * List of IDs.
     * @param IDs List of IDs
     * @param o Object that matches the returned Objects.
     * @return List of Objects
     */
    List<Object<O,A>> getEntities(List<O> IDs, Object<O,A> o);
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
    List<FCAAttribute<O,A>> computePrime (List<FCAObject<O,A>> objects, Object<O,A> o);

}
