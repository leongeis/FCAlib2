package api;

import fca.FCAAttribute;
import fca.FCAObject;

import java.util.List;

/**
 * Interface describing a Context in FCA.
 * A context class should have a List of Attributes,
 * as well as a List of Objects. These Lists can be
 * of type of the Interfaces (ObjectAPI and Attribute)
 * or of a own Class implementing these interfaces.
 * @param <O> Type of the Objects.
 * @param <A> Type of the Attributes.
 * @author Leon Geis
 */
public interface Context<O,A> {

    /**
     * Get all Objects, which correspond to the List of IDs.
     * Note: If an ID is not present in the Context,
     * <code>null</code> will be added instead.
     * @param IDs List of IDs
     * @return List of all Objects, corresponding to the List
     *          of IDs.
     */
    List<ObjectAPI<O,A>> getObjects(List<O> IDs);

    /**
     * Get all Attributes, which correspond to the List of IDs.
     * Note: If an ID is not present in the Context,
     * <code>null</code> will be added instead.
     * @param IDs List of IDs.
     * @return List of all Attributes, corresponding to the List
     *          of IDs.
     */
    List<Attribute<O,A>> getAttributes(List<A> IDs);

    /**
     * @return context ID of the context.
     */
    int getContextID();

    //TODO
    <T extends ObjectAPI<O,A>> void createObject(O o, Class<T> clazz);

    //TODO
    <T extends Attribute<O,A>> void createAttribute(A a, Class<T> clazz);

    /**
     * Checks if a given Attribute ID is contained in the Context.
     * @param a Attribute ID
     * @return <code>true</code> if Attribute ID is in List, <code>false</code>
     * otherwise.
     */
    boolean containsAttribute(A a);

    /**
     * Checks if a given Object ID is contained in the Context.
     * @param o Object ID
     * @return <code>true</code> if Object ID is in List, <code>false</code>
     * otherwise.
     */
    boolean containsObject(O o);

    /**
     * Adds an FCAObject Object to the Context, as well as the Attributes
     * the FCAObject Object has.
     * @param o FCAObject
     */
    void addFCAObject(FCAObject<O,A> o);

    /**
     * Adds an FCAAttribute Object to the Context, as well as all the
     * corresponding FCAObject Objects.
     * @param a FCAAttribute
     */
    void addFCAAttribute(FCAAttribute<O,A> a);

    /**
     * Get all Objects of the context.
     * @return List of FCAObjects.
     */
    List<ObjectAPI<O,A>> getContextObjects();

    /**
     * Get all Attributes of the context.
     * @return List of FCAAttributes.
     */
    List<Attribute<O,A>> getContextAttributes();

    /**
     * Returns a single Attribute of the Context.
     * @param a The ID of the Attribute to be returned.
     * @return Attribute Object, if the Attribute is in the
     * Attribute List of the Context, <code>null</code> otherwise.
     */
    Attribute<O,A> getAttribute(A a);

    /**
     * Returns a single Object of the Context.
     * @param o The ID of the Object to be returned.
     * @return FCAObject Object, if the Object is in the
     * Object List of the Context, <code>null</code> otherwise.
     */
    ObjectAPI<O,A> getObject(O o);

}
