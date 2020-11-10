package fca;
/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Describes a single Formal Context with
 * the corresponding Objects and Attributes.
 * The first type parameter describes the type
 * of the objects of the context and the second of
 * the corresponding attributes.
 *
 * @author Leon Geis
 */

public class FCAFormalContext<O,A> {

    /**
     * ID of the Context.
     */
    private static int contextID = 0;

    /**
     * Expert of the Context.
     */
    private FCAExpert<O,A> expert;

    /**
     * List of all Objects of the Context.
     */
    private List<FCAObject<O,A>> contextObjects;

    /**
     * List of all Attributes of the Context.
     */
    private List<FCAAttribute<O,A>> contextAttributes;

    /**
     * Constructor of the class creates two empty Lists
     * for the Attributes and the Objects and initializes the
     * FormalContext Object for computation purposes, as well
     * as an Expert Object.
     */
    public FCAFormalContext(){
        this.contextAttributes=new ArrayList<>();
        this.contextObjects=new ArrayList<>();
        //this.expert = new FCAExpert();
        //Increment ID
        contextID++;
    }

    /**
     * Creates an Object and adds it to the Context.
     * @param o ID of new Object
     */
    public void createFCAObject(O o){
        this.contextObjects.add(new FCAObject<>(o));
    }

    /**
     * Creates an Attribute and adds it to the Context.
     * @param a ID of new Attribute
     */
    public void createFCAAttribute(A a){
        this.contextAttributes.add(new FCAAttribute<>(a));
    }

    /**
     * Checks if a given Attribute ID is contained in the Context.
     * @param a Attribute ID
     * @return <code>true</code> if Attribute ID is in List, <code>false</code>
     * otherwise.
     */
    public boolean containsAttribute(A a){
        //Create a List for all Attribute IDs
        List<A> ids = new ArrayList<>();
        //Add all Attribute IDs to the List
        for(FCAAttribute<O,A> attr : this.contextAttributes){
            ids.add(attr.getAttributeID());
        }
        //Check if Parameter a is contained.
        return ids.contains(a);
    }
    /**
     * Checks if a given Object ID is contained in the Context.
     * @param o Object ID
     * @return <code>true</code> if Object ID is in List, <code>false</code>
     * otherwise.
     */
    public boolean containsObject(O o){
        //Create a List for all Object IDs
        List<O> ids = new ArrayList<>();
        //Add all Object IDs to the List
        for(FCAObject<O,A> obj : this.contextObjects){
            ids.add(obj.getObjectID());
        }
        //Check if Parameter a is contained.
        return ids.contains(o);
    }

    /**
     * Adds an FCAObject to the Context, as well as the Attributes
     * the FCAObject has.
     * @param o FCAObject
     */
    public void addFCAObject(FCAObject<O,A> o){
        //Add Object to Object List
        this.contextObjects.add(o);
        //For each Attribute of the new Object, check
        //if Attribute is already present in the Attribute List;
        //if not add the Attribute with the new Object to the Context.
        for(A attr : o.getAttributes()){
            if(!this.containsAttribute(attr)){
                FCAAttribute<O,A> newAttribute = new FCAAttribute<>(attr);
                newAttribute.addObject(o.getObjectID());
                this.contextAttributes.add(newAttribute);
            }
        }
    }

    /**
     * Adds an FCAAttribute to the Context, as well as all corresponding
     * FCAObjects.
     * @param a FCAAttribute
     */
    public void addFCAAttribute(FCAAttribute<O,A> a){
        //Add Attribute to Attribute List
        this.contextAttributes.add(a);
        //For each Object of the new Attribute, check
        //if Object is already present in the Object List;
        //if not add the Object with the new Attribute to the Context.
        for(O obj : a.getObjects()){
            if(!this.containsObject(obj)){
                FCAObject<O,A> newObject = new FCAObject<>(obj);
                newObject.addAttribute(a.getAttributeID());
                this.contextObjects.add(newObject);
            }
        }
    }


    /**
     * Get all Objects of the context.
     * @return List of FCAObjects.
     */
    public List<FCAObject<O,A>> getContextObjects(){
        return this.contextObjects;
    }

    /**
     * Get all Attributes of the context.
     * @return List of FCAAttributes.
     */
    public List<FCAAttribute<O,A>> getContextAttributes(){
        return this.contextAttributes;
    }

    /**
     * Returns a single Attribute of the Context.
     * @param a The Attribute to be returned.
     * @return FCAAttribute Object, if the Attribute is in the
     * Attribute List of the Context, <code>null</code> otherwise.
     */
    public FCAAttribute<O,A> getAttribute(A a){
        for(FCAAttribute<O,A> attr : this.contextAttributes){
            if(attr.getAttributeID().equals(a))return attr;
        }
        return null;
    }
    /**
     * Returns a single Object of the Context.
     * @param o The Object to be returned.
     * @return FCAObject Object, if the Object is in the
     * Object List of the Context, <code>null</code> otherwise.
     */
    public FCAObject<O,A> getObject(O o){
        for(FCAObject<O,A> obj : this.contextObjects){
            if(obj.getObjectID().equals(o))return obj;
        }
        return null;
    }

    //TODO Efficient Implementation
    /**
     * Computes all Concepts of the Context.
     * All Concepts are of the form (A,A')
     * with A⊆G. G is the set of all Objects.
     * Note: Naive Implementation.
     */
    public List<FCAConcept<O,A>> computeAllConcepts(){
        //Create List of all Concepts of this Context, which will be returned at the end.
        List<FCAConcept<O,A>> concepts = new ArrayList<>();

        //First add smallest extent (All Objects and the Attributes they have in common)
        FCAConcept<O,A> first = new FCAConcept<>();

        //Add Concept (G,G') to the List
        first.setExtent(new ArrayList<>(this.contextObjects));
        first.setIntent(getObjectPrime(this.contextObjects));
        concepts.add(first);

        //Iterate over each Attribute
        for(FCAAttribute<O,A> a : this.contextAttributes){
            //Calculate the Prime for this Attribute
            //Use getAttributePrime Method; Hence the Parameter
            //Collections.singletonList.
            //Equivalently, one can use here a.getObjects();
            List<FCAObject<O,A>> prime = getAttributePrime(Collections.singletonList(a));

            //Calculate the Intersection of the prime and each set of Objects in the list
            //--> A ∩ m'
            //Create Temporary List to save new sets of Objects
            List<FCAConcept<O,A>> temp = new ArrayList<>();
            for(FCAConcept<O,A> con : concepts){
                //Copy prime FCAObject List
                List<FCAObject<O,A>> intersection = new ArrayList<>(prime);
                //Perform Intersection using retainAll and the set of Objects of each concept
                intersection.retainAll(con.getExtent());
                //Check if Intersection is contained in List of all current Objects;
                //if not add it to the temporary List
                if(!concepts.stream().map(FCAConcept::getExtent).collect(Collectors.toList()).contains(intersection)){
                    FCAConcept<O,A> concept = new FCAConcept<>();
                    concept.setExtent(intersection);
                    //Check if Extent is empty
                    //If it is: Add Intent as (∅,∅')
                    if(concept.getExtent().isEmpty()){
                        concept.setIntent(this.getContextAttributes());
                    }else{
                        concept.setIntent(getObjectPrime(intersection));
                    }
                    //Add Concept to Temporary List
                    temp.add(concept);
                }
            }
            //Only add non-redundant sets
            for(FCAConcept<O,A> c : temp){
                if(!concepts.stream().map(FCAConcept::getExtent).collect(Collectors.toList()).contains(c.getExtent())){
                    concepts.add(c);
                }
            }
        }
        //Return complete List of Concepts
        return concepts;
    }

    //TODO INCREASE PERFORMANCE
    /**
     * Compute the Prime of a List of FCAObjects.
     * @param o List of FCAObjects.
     * @return List of FCAAttributes all of the FCAObjects
     * have in common.
     */
    public List<FCAAttribute<O,A>> getObjectPrime(List<FCAObject<O,A>> o){
        //First get all Lists of Attributes of the Objects; List of Lists
        List<List<A>> allAttributes = new ArrayList<>();
        //Iterate over each Object and add the Attributes of this Object to the List
        for(FCAObject<O,A> obj : o){
            allAttributes.add(obj.getAttributes());
        }
        if(allAttributes.size()>0) {
            //Create Intersection of Lists of Attributes to be returned and
            //add first element (List) of allAttributes to create a starting point
            List<A> intersection = new ArrayList<>(allAttributes.get(0));
            for(ListIterator<List<A>> iterator = allAttributes.listIterator(0); iterator.hasNext();){
                intersection.retainAll(iterator.next());
            }
            //Add corresponding Attribute Objects to be returned to new List
            List<FCAAttribute<O,A>> returnList = new ArrayList<>();
            for (A a : intersection) {
                returnList.add(getAttribute(a));
            }
            return returnList;
        }
        //If no matching Element is found return new empty List
        return new ArrayList<>();
    }

    //TODO INCREASE PERFORMANCE
    /**
     * Compute the Prime of a List of FCAAttributes.
     * @param a List of FCAAttributes.
     * @return List of FCAObjects all of the FCAAttributes
     * have in common.
     */
    public List<FCAObject<O,A>> getAttributePrime(List<FCAAttribute<O,A>> a){
        //First get all Lists of Objects of the Attributes; List of Lists
        List<List<O>> allObjects = new ArrayList<>();
        //Iterate over each Attribute and add the Objects of this Attribute to the List
        for(FCAAttribute<O,A> attr : a){
            allObjects.add(attr.getObjects());
        }
        if(allObjects.size()>0) {
            //Create Intersection of Lists of Objects to be returned and
            //add first element (List) of allObjects to create a starting point
            List<O> intersection = new ArrayList<>(allObjects.get(0));
            for(ListIterator<List<O>> iterator = allObjects.listIterator(0); iterator.hasNext();){
                intersection.retainAll(iterator.next());
            }
            //Add corresponding Object Attributes to be returned to new List
            List<FCAObject<O,A>> returnList = new ArrayList<>();
            for (O o : intersection) {
                returnList.add(getObject(o));
            }
            return returnList;
        }
        //If no matching Element is found return new empty List
        return new ArrayList<>();
    }


    public void computeStemBase(){

    }

    public void nextClosure(){

    }

}
