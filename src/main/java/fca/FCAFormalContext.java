package fca;
/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import api.Attribute;
import api.Context;
import api.ObjectAPI;
import utils.IndexedList;
import utils.Pair;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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

public abstract class FCAFormalContext<O,A> implements Context<O,A> {

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
    private List<ObjectAPI<O,A>> contextObjects;

    /**
     * List of all Attributes of the Context.
     */
    private List<Attribute<O,A>> contextAttributes;

    /**
     * Type of the Objects of the Context.
     */
    private final Type objectType;

    /**
     * Type of the Attributes of the Context.
     */
    private final Type attributeType;

    /**
     * Constructor of the class creates two empty Lists
     * for the Attributes and the Objects and initializes the
     * FormalContext Object for computation purposes, as well
     * as an Expert Object.
     * Making use of an anonymous inner class to store the type parameter
     * during runtime.
     */
    public FCAFormalContext(){
        this.contextAttributes=new ArrayList<>();
        this.contextObjects=new ArrayList<>();
        //Save Types
        Type type = getClass().getGenericSuperclass();
        this.objectType = ((ParameterizedType)type).getActualTypeArguments()[0];
        this.attributeType = ((ParameterizedType)type).getActualTypeArguments()[1];
        //this.expert = new FCAExpert();
        //Increment ID
        contextID++;
    }

    /**
     * @return context ID of the context.
     */
    public int getContextID() {
        return contextID;
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
        for(Attribute<O,A> attr : this.contextAttributes){
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
        for(ObjectAPI<O,A> obj : this.contextObjects){
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
        for(A attr : o.getDualEntities()){
            if(!this.containsAttribute(attr)){
                FCAAttribute<O,A> newAttribute = new FCAAttribute<>(attr);
                newAttribute.addObject(o.getObjectID());
                this.contextAttributes.add(newAttribute);
            }else {
                //Check if other Objects have this Attribute
                for(ObjectAPI<O,A> object : this.getContextObjects()){
                    //If an Object has this Attribute add it to the Object
                    //List of the Attribute. Care for the case that the Object List
                    //of the Attribute already contains the Object.
                    if(object.getDualEntities().contains(attr) && !getAttribute(attr).getDualEntities().contains(object.getObjectID())){
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
        for(O obj : a.getDualEntities()){
            if(!this.containsObject((obj))){
                FCAObject<O,A> newObject = new FCAObject<>(obj);
                newObject.addAttribute(a.getAttributeID());
                this.contextObjects.add(newObject);
            }else{
                //Check if other Attributes have this Object
                for(Attribute<O,A> atr : this.getContextAttributes()){
                    //If an Attribute has this Object add it to the Attribute
                    //List of the Object. Care for the case that the Attribute List
                    //of the Object already contains the Attribute.
                    if(atr.getDualEntities().contains(obj) && !getObject(obj).getDualEntities().contains(atr.getAttributeID())){
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
    public List<ObjectAPI<O,A>> getContextObjects(){
        return this.contextObjects;
    }

    /**
     * Get all Attributes of the context.
     * @return List of FCAAttributes.
     */
    public List<Attribute<O,A>> getContextAttributes(){
        return this.contextAttributes;
    }

    /**
     * Returns a single Attribute of the Context.
     * @param a The Attribute to be returned.
     * @return FCAAttribute Object, if the Attribute is in the
     * Attribute List of the Context, <code>null</code> otherwise.
     */
    public Attribute<O,A> getAttribute(A a){
        for(Attribute<O,A> attr : this.contextAttributes){
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
    public ObjectAPI<O,A> getObject(O o){
        for(ObjectAPI<O,A> obj : this.contextObjects){
            if(obj.getObjectID().equals(o))return obj;
        }
        return null;
    }

    /**
     * Get all Attributes, which correspond to the List of IDs.
     * @param IDs List of IDs
     * @param <T> Attribute Interface or any subtype.
     * @return List of all Attributes, corresponding to the List
     *          of IDs.
     */
    @SuppressWarnings("unchecked")
    public <T extends Attribute<O,A>> List<T> getAttributes(List<A> IDs){
        //First check if the List of IDs is empty
        if(IDs.isEmpty()){
            //If it is return empty List;
            return new ArrayList<>();
            //If not check if Type of the IDs equals the Type of the Attributes
            //of the context.
        }else if(this.attributeType.equals(IDs.get(0).getClass())){
            //Create HashSet to be returned
            HashSet<T> set = new HashSet<>();
            //Get each Attribute according to the List of IDs
            for(A a: IDs){
                set.add((T)getAttribute(a));
            }
            //Return List of all Attributes
            return new ArrayList<>(set);
        }else{
            //If the Type of the List is not equal the Type of the Attributes of this context
            //return an empty List
            return new ArrayList<>();
        }
    }

    /**
     * Get all Objects, which correspond to the List of IDs.
     * @param IDs List of IDs
     * @param <T> ObjectAPI Interface or any subtype.
     * @return List of all Objects, corresponding to the List
     *          of IDs.
     */
    @SuppressWarnings("unchecked")
    public <T extends ObjectAPI<O,A>> List<T> getObjects(List<O> IDs){
        //First check if the List of IDs is empty
        if(IDs.isEmpty()){
            //If it is return empty List;
            return new ArrayList<>();
            //If not check if Type of the IDs equals the Type of the Objects
            //of the context.
        }else if(this.objectType.equals(IDs.get(0).getClass())){
            //Create HashSet to be returned
            HashSet<T> set = new HashSet<>();
            //Get each Object according to the List of IDs
            for(O o: IDs){
                set.add((T)getObject(o));
            }
            //Return List of all Objects
            return new ArrayList<>(set);
        }else{
            //If the Type of the List is not equal the Type of the Objects of this context
            //return an empty List
            return new ArrayList<>();
        }
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
        first.setIntent(computePrimeOfObjects(this.contextObjects));
        concepts.add(first);

        //Iterate over each Attribute
        for(Attribute<O,A> a : this.contextAttributes){
            //Calculate the Prime for this Attribute
            //Use getAttributePrime Method; Hence the Parameter
            //Collections.singletonList.
            //Equivalently, one can use here a.getObjects();
            List<? extends ObjectAPI<O,A>> prime = computePrimeOfAttributes(Collections.singletonList(a));

            //Calculate the Intersection of the prime and each set of Objects in the list
            //--> A ∩ m'
            //Create Temporary List to save new sets of Objects
            List<FCAConcept<O,A>> temp = new ArrayList<>();
            for(FCAConcept<O,A> con : concepts){
                //Copy prime FCAObject List
                List<ObjectAPI<O,A>> intersection = new ArrayList<>(prime);
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
                        concept.setIntent(computePrimeOfObjects(intersection));
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

    /**
     * Computes all Concepts of the Context.
     * Uses a List of computed Closures to compute
     * the corresponding concepts and returns them.
     * @param closures List of Closures
     * @return List of Concepts of the Context
     */
    public List<FCAConcept<O,A>> computeAllConcepts(List<List<Attribute<O,A>>> closures){
        //Create List of FCAConcepts that will be returned
        List<FCAConcept<O,A>> concepts = new ArrayList<>();
        //Go through List of closures and create a FCAConcept Object
        //for each closure. Also compute the prime and set the Extent accordingly.
        for(List<Attribute<O,A>> list : closures){
            FCAConcept<O,A> concept = new FCAConcept<>();
            concept.setIntent(list);
            concept.setExtent(computePrimeOfAttributes(list));
            concepts.add(concept);
        }
        //Return List of concepts
        return concepts;
    }

    /**
     * Compute the Prime of a List of FCAObjects.
     * @param o List of FCAObjects.
     * @return List of FCAAttributes all of the FCAObjects
     * have in common.
     */
    @SuppressWarnings("unchecked")
    public <T extends Attribute<O,A>> List<T> computePrimeOfObjects(List<? extends ObjectAPI<O, A>> o){
        //If the List is empty return G (all Objects)
        if(o.isEmpty()){
            return new ArrayList<>((Collection<? extends T>) this.contextAttributes);
        }else {
            //Create HashSet that contains all IDs of the Attributes of the Objects
            HashSet<A> IDs = new HashSet<>();
            //Set boolean to indicate the first element
            boolean first = true;
            //Iterate over each Attribute and add the Object IDs of this Attribute to the List
            for (ObjectAPI<O, A> object : o) {
                if(first){
                    IDs.addAll((Collection<? extends A>) object.getDualEntities());
                    first = false;
                }else{
                    IDs.retainAll(object.getDualEntities());
                }

            }
            //Create List to be returned
            List<T> objects = new ArrayList<>();
            //Get corresponding List of Objects
            for(A a : IDs){
                objects.add((T)getAttribute(a));
            }
            return objects;
        }
    }

    /**
     * Compute the Prime of a List of FCAAttributes.
     * @param a List of FCAAttributes.
     * @return List of FCAObjects all of the FCAAttributes
     * have in common.
     */
    @SuppressWarnings("unchecked")
    public <T extends ObjectAPI<O,A>> List<T> computePrimeOfAttributes(List<? extends Attribute<O, A>> a){
        //If the List is empty return G (all Objects)
        if(a.isEmpty()){
            return new ArrayList<>((Collection<? extends T>) this.contextObjects);
        }else {
            //Create HashSet that contains all IDs of the Objects of the Attributes
            HashSet<O> IDs = new HashSet<>();
            //Set boolean to indicate the first element
            boolean first = true;
            //Iterate over each Attribute and add the Object IDs of this Attribute to the List
            for (Attribute<O, A> attr : a) {
                if(first) {
                    IDs.addAll((Collection<? extends O>) attr.getDualEntities());
                    first=false;
                }else{
                    IDs.retainAll(attr.getDualEntities());
                }
            }
            //Create List to be returned
            List<T> objects = new ArrayList<>();
            //Get corresponding List of Objects
            for(O o : IDs){
                objects.add((T)getObject(o));
            }
            return objects;
        }
    }

    /**
     * Computes all Closures of the Context.
     * Note: Currently only Attribute Closures are computed.
     */
    public List<List<Attribute<O,A>>> computeAllClosures(boolean... print){
        //Setting the Optional Parameter to False
        boolean printBool = print.length >= 1 && print[0];
        //Create Lectical Order on all Attributes
        IndexedList<Attribute<O,A>> indexedAttributes = new IndexedList<>(this.getContextAttributes());
        //Create List of Lists of Attributes (Closures) to be returned
        List<List<Attribute<O,A>>> closures = new ArrayList<>();
        //Set the lectically first Set
        IndexedList<Attribute<O,A>> A = firstClosure(indexedAttributes);
        if(printBool)System.out.println("CLOSURES:");
        while(A != null){
            //Print (Optional)
            if(printBool)System.out.println(A.getIndexedList().stream().map(Pair::getLeft).map(Attribute::getAttributeID).collect(Collectors.toList()));
            //Add A to the List to be returned
            closures.add(A.getIndexedList().stream().map(Pair::getLeft).collect(Collectors.toList()));
            A = nextClosure(A, indexedAttributes);
        }
        //Return the List of all Closures
        return closures;
    }

    /**
     * Computes the first ClosureOperator, i.e., ∅''.
     * @param index Indexed Attribute List
     * @return First ClosureOperator as IndexedList
     */
    private IndexedList<Attribute<O,A>> firstClosure(IndexedList<Attribute<O,A>> index){
        //Create a new IndexedList
        IndexedList<Attribute<O,A>> closure = new IndexedList<>();
        //Go through each Attribute of ∅'' and add the corresponding Pair to the
        //newly created IndexedList
        for(Attribute<O,A> attr : computePrimeOfObjects(this.getContextObjects())){
            closure.add(index.getPair(index.getIndex(attr)));
        }
        //return ∅''
        return closure;
    }

    /**
     * Computes the lectically next ClosureOperator of the given first parameter.
     * @param next The List from which the next ClosureOperator should be computed.
     * @param indexed The Indexed Attribute List of the Context.
     * @return IndexedList of the Next ClosureOperator.
     */
    private IndexedList<Attribute<O,A>> nextClosure(IndexedList<Attribute<O,A>> next, IndexedList<Attribute<O,A>> indexed){
        //Go through List of all Attributes in reverse Order
        //Provide listIterator Parameter with size of the List to get a pointer
        //to the end of the list.
        ListIterator<Pair<Attribute<O,A>,Integer>> iterator = indexed.getIndexedList().listIterator(indexed.getIndexedList().size());
        while(iterator.hasPrevious()){
            //Get the previous Pair of Attribute and Index
            Pair<Attribute<O,A>,Integer> previous = iterator.previous();
            //If the Attribute is contained in next remove it
            if(next.contains(previous.getLeft())){
                next.remove(previous.getLeft());
            }else{
                //Create union of the current List and the previous Attribute
                IndexedList<Attribute<O,A>> union = new IndexedList<>();
                union.add(previous);
                union.addAll(next.getIndexedList());
                //Compute the double Prime of the union and save it
                List<Attribute<O,A>> B = computePrimeOfObjects(computePrimeOfAttributes(union.getObjects()));
                //Remove all Attributes from B that are in next
                B.removeAll(next.getObjects());
                //Use a flag to verify if Attributes are < than previous (m)
                boolean flag=true;
                //Go through each Attribute left in B and check
                //if it is smaller (lectically) than previous (m)
                //If it is set the flag to false and stop.
                for(Attribute<O,A> a : B){
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
                    IndexedList<Attribute<O,A>> ret = new IndexedList<>();
                    //Again compute the double prime of the union and add each attribute as a pair
                    //Using the indexed Attribute List to get an index for each Attribute
                    for(Attribute<O,A> a : computePrimeOfObjects(computePrimeOfAttributes(union.getObjects()))){
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

    /**
     * Computes the ClosureOperator of a List of Attribute w.r.t a List of
     * Implications in linear time. The provided List of Attributes will be modified!
     * @param attributes List of Attributes
     * @param implications List of Implications
     * @return ClosureOperator of the List of Attributes
     */
    public List<Attribute<O,A>> computeLinClosure(List<Attribute<O,A>> attributes, List<FCAImplication<O,A>> implications){

        //Create List of Pairs. Here, the Pair consists on the left side of
        //a single FCAAttribute, and on the right side a List of Implications.
        //Note: The left side is always the Attribute itself
        //      and the right side are Implications which contain this Attribute
        //      in their premise.
        List<Pair<Attribute<O,A>,List<FCAImplication<O,A>>>> attrList = new ArrayList<>();

        //Create List of Pairs, with Implications on the left and Integers, describing the size of the premise
        //on the right
        List<Pair<FCAImplication<O,A>,Integer>> count = new ArrayList<>();

        //Go through each Implication provided via parameter
        for(FCAImplication<O,A> implication : implications){
            //Save the size of the implication and save the pair in count
            count.add(new Pair<>(implication,implication.getPremise().size()));
            //Check if left side of the implication is empty
            if(implication.getPremise().isEmpty()){
                //Add the conclusion to the set of attributes provided via parameter
                attributes.addAll(implication.getConclusion());

                //Remove duplicate Elements using Stream API
                //TODO Rework assigned data structure (currently simple ArrayList 20.10.20)
                attributes = attributes.stream().distinct().collect(Collectors.toList());
            }
            //Go through each Attribute in the Premise of the Implication
            for(Attribute<O,A> attribute : implication.getPremise()){
                //Get a List of all Attributes on the left side
                List<Attribute<O,A>> lhsAttributes = attrList.stream().map(Pair::getLeft).collect(Collectors.toList());
                //If this List contains the variable 'attribute', get the Pair of that variable and
                //add the Implication to the right side
                if(lhsAttributes.contains(attribute)){
                    //TODO DECREASE COMPLEXITY OF THIS STEP BELOW
                    //Go through each Pair of the attrList and if the left side of a pair,
                    //matches the variable 'attribute', add the implication to the right side.
                    for(Pair<Attribute<O,A>,List<FCAImplication<O,A>>> p : attrList){
                        if(p.getLeft().equals(attribute)){
                            p.getRight().add(implication);
                            break;
                        }
                    }
                }
                //If not, add the variable 'attribute' with a Singleton List containing
                //the current Implication
                else{
                    List<FCAImplication<O,A>> implList = new ArrayList<>();
                    implList.add(implication);
                    attrList.add(new Pair<>(attribute,implList));
                }
            }
        }
        //Create a copy of the attribute List, which is provided via parameter and (possibly) changed
        //during computation above:
        List<Attribute<O,A>> updated = new ArrayList<>(attributes);

        //While the copy (updated) is not empty
        while(!updated.isEmpty()){
            //Get an Attribute out of the List and remove it afterwards from the list
            Iterator<Attribute<O,A>> iterator = updated.iterator();
            Attribute<O,A> m = iterator.next();
            updated.remove(m);
            //Get the List of Implications that contain m in their premise
            List<FCAImplication<O,A>> implicationsOfm = new ArrayList<>();
            for(Pair<Attribute<O,A>,List<FCAImplication<O,A>>> p : attrList){
                if(p.getLeft().equals(m)){
                    implicationsOfm = p.getRight();
                    break;
                }
            }
            //Now, go through each Implication of m
            for(FCAImplication<O,A> implOfm : implicationsOfm){
                //Get the pair of the implication and decrease the right side by 1
                for(Pair<FCAImplication<O,A>,Integer> countPair : count){
                    if(countPair.getLeft().equals(implOfm)){
                        countPair.setRight(countPair.getRight()-1);
                        //If the right side is now 0
                        if(countPair.getRight()==0){
                            //Add the conclusion of the implication without elements from
                            //the attribute List (provided via parameter) to this list
                            //First create a copy of the conclusion
                            List<Attribute<O,A>> conclusionCopy = new ArrayList<>(implOfm.getConclusion());
                            //Remove all Elements that are in the provided Attribute List from the Copy
                            conclusionCopy.removeAll(attributes);
                            //Add this copy now to the attribute List provided via parameter
                            //and the 'updated' List
                            attributes.addAll(conclusionCopy);
                            updated.addAll(conclusionCopy);
                            //TODO Check for redundant elements
                        }
                    }
                }
            }
        }
        //Return the closure of the provided List
        return attributes;
    }

    /**
     * Computes the Stem Base of the current Context.
     * @return List of all Implications of the Stem Base
     */
    public List<FCAImplication<O,A>> computeStemBase(){
        //Create IndexedList of the Attributes of the Context
        IndexedList<Attribute<O,A>> indexedAttributes = new IndexedList<>(this.contextAttributes);
        //Create new List and set it to the closure of the empty set
        List<Attribute<O,A>> A = firstClosure(indexedAttributes).getObjects();
        //Create empty List for the Implications
        List<FCAImplication<O,A>> implList = new ArrayList<>();
        //If A is not empty add first Implication to Implication List
        if(!A.isEmpty()){
            implList.add(new FCAImplication<>(new ArrayList<>(),A));
        }
        //Get the Attribute with the highest Index in the Attribute List
        Attribute<O,A> max = indexedAttributes.getMaxElement();
        while (!A.equals(indexedAttributes.getObjects())){
            //Get all attributes smaller than 'max' and traverse them in reverse order
            List<Attribute<O,A>> smallerAttributes = new ArrayList<>();
            for(Pair<Attribute<O,A>,Integer> indexed : indexedAttributes.getIndexedList()){
                if(indexed.getRight()<=indexedAttributes.getIndex(max)){
                    smallerAttributes.add(indexed.getLeft());
                }
            }
            //Reverse the order
            //Collections.reverse(smallerAttributes);
            ListIterator<Attribute<O,A>> iterator = smallerAttributes.listIterator(smallerAttributes.size());
            while(iterator.hasPrevious()){
                Attribute<O,A> atr = iterator.previous();
                if(A.contains(atr)){
                    A.remove(atr);
                }else {
                    //Create Copy of A and add the current Attribute 'atr'
                    List<Attribute<O,A>> attrCopy = new ArrayList<>(A);
                    attrCopy.add(atr);
                    //Compute the ClosureOperator of this copy List
                    List<Attribute<O,A>> B = computeLinClosure(attrCopy,implList);
                    //Remove A from B
                    B.removeAll(A);
                    //Go through each remaining Attribute in B and check if no element lectically smaller than
                    //'atr' is contained
                    //Create boolean variable
                    boolean flag = true;
                    for(Attribute<O,A> atrInB : B){
                        if(indexedAttributes.getIndex(atrInB) < indexedAttributes.getIndex(atr)){
                            flag = false;
                            break;
                        }
                    }
                    //Now if the flag is still true:
                    if(flag){
                        //Add back the Elements from A to B and set A to B
                        B.addAll(A);
                        A = B;
                        //Set max Attribute to current Attribute
                        max = atr;
                        //Exit for loop
                        break;
                    }
                }
            }
            //if A is not equal its closure add corresponding Implication
            //Compute ClosureOperator of A
            List<Attribute<O,A>> closureA = computePrimeOfObjects(computePrimeOfAttributes(A));
            if(!A.containsAll(closureA) && A.size()!=closureA.size()){
                //Check if the size of the Closure is not equal the size of all Attributes
                //If it is remove all Attribute from the Conclusion that are contained in the premise
                if(closureA.size()!=this.contextAttributes.size())closureA.removeAll(A);
                //Add the implication to the List
                implList.add(new FCAImplication<>(A,closureA));
            }

            //Remove the closure of A from A itself
            List<Attribute<O,A>> closureWithoutA = new ArrayList<>(closureA);
            closureWithoutA.removeAll(A);
            //Go through each Attribute in closureWithoutA and
            //if no Attribute smaller than 'max' is contained
            //then set A to closureA and max to the largest Attribute
            //in the IndexedList of Attributes
            //Set boolean flag to true
            boolean flag = true;
            for(Attribute<O,A> atr : closureWithoutA){
                if(indexedAttributes.getIndex(atr)<indexedAttributes.getIndex(max)){
                    flag = false;
                    break;
                }
            }
            //If the flag is still true perform steps mentioned above:
            if(flag){
                A = new ArrayList<>(closureA);
                //set max to the largest Attribute in Indexed Attribute List
                max = indexedAttributes.getMaxElement();
            }else{
                //Get List of all Attributes in A that are smaller equal than max
                List<Attribute<O,A>> smaller = new ArrayList<>();
                for(Attribute<O,A> atr : A){
                    if(indexedAttributes.getIndex(atr)<=indexedAttributes.getIndex(max)){
                        smaller.add(atr);
                    }
                }
                //Set A to 'smaller'
                A = smaller;
            }
        }

        return implList;
    }

    public void nextIntent(){

    }

    public void nextExtent(){

    }



}
