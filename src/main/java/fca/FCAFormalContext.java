package fca;
/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import utils.IndexedList;
import utils.Pair;

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
            }else {
                //Check if other Objects have this Attribute
                for(FCAObject<O,A> object : this.getContextObjects()){
                    //If an Object has this Attribute add it to the Object
                    //List of the Attribute. Care for the case that the Object List
                    //of the Attribute already contains the Object.
                    if(object.getAttributes().contains(attr) && !getAttribute(attr).getObjects().contains(object.getObjectID())){
                        getAttribute(attr).addObject(object.getObjectID());
                    }
                }
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
            }else{
                //Check if other Attributes have this Object
                for(FCAAttribute<O,A> atr : this.getContextAttributes()){
                    //If an Attribute has this Object add it to the Attribute
                    //List of the Object. Care for the case that the Attribute List
                    //of the Object already contains the Attribute.
                    if(atr.getObjects().contains(obj) && !getObject(obj).getAttributes().contains(atr.getAttributeID())){
                        getObject(obj).addAttribute(atr.getAttributeID());
                    }
                }
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


    public List<FCAConcept<O,A>> computeAllConcepts(IndexedList<FCAAttribute<O,A>> index){
        return null;
    }

    /**
     * Compute the Prime of a List of FCAObjects.
     * @param o List of FCAObjects.
     * @return List of FCAAttributes all of the FCAObjects
     * have in common.
     */
    public List<FCAAttribute<O,A>> getObjectPrime(List<FCAObject<O,A>> o){
        //If the List is empty return M (all Attributes)
        if(o.isEmpty())return new ArrayList<>(this.contextAttributes);
        //Else:
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

    /**
     * Compute the Prime of a List of FCAAttributes.
     * @param a List of FCAAttributes.
     * @return List of FCAObjects all of the FCAAttributes
     * have in common.
     */
    public List<FCAObject<O,A>> getAttributePrime(List<FCAAttribute<O,A>> a){
        //If the List is empty return G (all Objects)
        if(a.isEmpty())return new ArrayList<>(this.contextObjects);
        //Else:
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

    /**
     * Computes all Closures of the Context.
     * Note: Currently only Attribute Closures are computed.
     */
    public void computeAllClosures(){
        //Create Lectical Order on all Attributes
        IndexedList<FCAAttribute<O,A>> indexedAttributes = new IndexedList<>(this.getContextAttributes());
        //Set the lectically first Set
        IndexedList<FCAAttribute<O,A>> A = firstClosure(indexedAttributes);
        System.out.println("CLOSURES:");
        while(A != null){
            //Print
            System.out.println(A.getIndexedList().stream().map(Pair::getLeft).map(FCAAttribute::getAttributeID).collect(Collectors.toList()));
            A = nextClosure(A, indexedAttributes);
        }
    }

    /**
     * Computes the first Closure, i.e., ∅''.
     * @param index Indexed Attribute List
     * @return First Closure as IndexedList
     */
    private IndexedList<FCAAttribute<O,A>> firstClosure(IndexedList<FCAAttribute<O,A>> index){
        //Create a new IndexedList
        IndexedList<FCAAttribute<O,A>> closure = new IndexedList<>();
        //Go through each Attribute of ∅'' and add the corresponding Pair to the
        //newly created IndexedList
        for(FCAAttribute<O,A> attr : getObjectPrime(this.getContextObjects())){
            closure.add(index.getPair(index.getIndex(attr)));
        }
        //return ∅''
        return closure;
    }

    /**
     * Computes the lectically next Closure of the given first parameter.
     * @param next The List from which the next Closure should be computed.
     * @param indexed The Indexed Attribute List of the Context.
     * @return IndexedList of the Next Closure.
     */
    public IndexedList<FCAAttribute<O, A>> nextClosure(IndexedList<FCAAttribute<O,A>> next, IndexedList<FCAAttribute<O,A>> indexed){
        //Go through List of all Attributes in reverse Order
        //Provide listIterator Parameter with size of the List to get a pointer
        //to the end of the list.
        ListIterator<Pair<FCAAttribute<O,A>,Integer>> iterator = indexed.getIndexedList().listIterator(indexed.getIndexedList().size());
        while(iterator.hasPrevious()){
            //Get the previous Pair of Attribute and Index
            Pair<FCAAttribute<O,A>,Integer> previous = iterator.previous();
            //If the Attribute is contained in next remove it
            if(next.contains(previous.getLeft())){
                next.remove(previous.getLeft());
            }else{
                //Create union of the current List and the previous Attribute
                IndexedList<FCAAttribute<O,A>> union = new IndexedList<>();
                union.add(previous);
                union.addAll(next.getIndexedList());
                //Compute the double Prime of the union and save it
                List<FCAAttribute<O,A>> B = getObjectPrime(getAttributePrime(union.getObjects()));
                //Remove all Attributes from B that are in next
                B.removeAll(next.getObjects());
                //Use a flag to verify if Attributes are < than previous (m)
                boolean flag=true;
                //Go through each Attribute left in B and check
                //if it is smaller (lectically) than previous (m)
                //If it is set the flag to false and stop.
                for(FCAAttribute<O,A> a : B){
                    //Use the indexed Attribute List to get the index
                    if(indexed.getIndex(a) < previous.getRight()){
                        flag = false;
                        break;
                    }
                }
                //Now if the flag is still true, which means that there is not element in B,
                //which is smaller than previous (m), create a indexed List and return it.
                if(flag){
                    //Create the List
                    IndexedList<FCAAttribute<O,A>> ret = new IndexedList<>();
                    //Again compute the double prime of the union and add each attribute as a pair
                    //Using the indexed Attribute List to get an index for each Attribute
                    for(FCAAttribute<O,A> a : getObjectPrime(getAttributePrime(union.getObjects()))){
                        ret.add(new Pair<>(a,indexed.getIndex(a)));
                    }
                    //Return the IndexedList
                    return ret;
                }
            }
        }
        //If all Attributes are traversed in reverse Order and none of the
        //above statements fits, return null.
        return null;
    }

    public void nextIntent(){

    }

    public void nextExtent(){

    }

    public void computeStemBase(){

    }


}
