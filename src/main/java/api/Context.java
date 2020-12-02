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
     * Returns all Attributes/Objects, which correspond to the
     * List of IDs.
     * @param IDs List of IDs
     * @param clazz Class of the Entity, which can either be Attribute.class
     *              or ObjectAPI.class
     * @return List of Attributes/Objects
     */
    <T extends ClosureOperator> List<T> getEntities(List<?> IDs, Class<T> clazz);

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
