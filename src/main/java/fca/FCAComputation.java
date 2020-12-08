package fca;

import api.Attribute;
import api.Context;
import api.ObjectAPI;
import utils.IndexedList;
import utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class used for the different algorithms of FCA.
 * @author Leon Geis
 */

//TODO implement interface + GENERIFY + JavaDoc rework
public class FCAComputation {

    /**
     * Compute the Prime of a List of FCAObjects.
     * @param o List of FCAObjects.
     * @return List of FCAAttributes all of the FCAObjects
     * have in common.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Context<O,A>,O,A> List<Attribute<O,A>> computePrimeOfObjects(List<? extends ObjectAPI<O, A>> objects, T context){
        //If the List is empty return M (all Attributes)
        if(objects.isEmpty()){
            return new ArrayList<>(context.getContextAttributes());
        }else {
            //Create HashSet that contains all IDs of the Attributes of the Objects
            HashSet<A> IDs = new HashSet<>();
            //Set boolean to indicate the first element
            boolean first = true;
            //Iterate over each Attribute and add the Object IDs of this Attribute to the List
            for (ObjectAPI<O, A> object : objects) {
                if(first){
                    IDs.addAll((Collection<? extends A>) object.getDualEntities());
                    first = false;
                }else{
                    IDs.retainAll(object.getDualEntities());
                }

            }
            //Create List to be returned
            List<Attribute<O,A>> attributes = new ArrayList<>();
            //Get corresponding List of Objects
            for(A a : IDs){
                attributes.add(context.getAttribute(a));
            }
            return attributes;
        }
    }

    /**
     * Compute the Prime of a List of FCAAttributes.
     * @param a List of FCAAttributes.
     * @return List of FCAObjects all of the FCAAttributes
     * have in common.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Context<O,A>,O,A> List<ObjectAPI<O,A>> computePrimeOfAttributes(List<? extends Attribute<O, A>> a, T context){
        //If the List is empty return G (all Objects)
        if(a.isEmpty()){
            return new ArrayList<>(context.getContextObjects());
        }else {
            //Create HashSet that contains all IDs of the Objects of the Attributes
            HashSet<O> IDs = new HashSet<>();
            //Set boolean to indicate the first element
            boolean first = true;
            //Iterate over each Attribute and add the Object IDs of this Attribute to the List
            for (Attribute<O, A> attr : a) {
                //Check if the context contains this attribute
                if(context.containsAttribute(attr.getAttributeID())) {
                    if (first) {
                        IDs.addAll((Collection<? extends O>) attr.getDualEntities());
                        first = false;
                    } else {
                        IDs.retainAll(attr.getDualEntities());
                    }
                }
            }
            //Create List to be returned
            List<ObjectAPI<O,A>> objects = new ArrayList<>();
            //Get corresponding List of Objects
            for(O o : IDs){
                objects.add(context.getObject(o));
            }
            return objects;
        }
    }

    /**
     * Computes all Concepts of the Context.
     * All Concepts are of the form (A,A')
     * with A⊆G. G is the set of all Objects.
     * Note: Naive Implementation.
     */
    public static <T extends Context<O,A>,O,A> List<FCAConcept<O,A>> computeAllConcepts(T context){
        //Create List of all Concepts of this Context, which will be returned at the end.
        List<FCAConcept<O,A>> concepts = new ArrayList<>();

        //First add smallest extent (All Objects and the Attributes they have in common)
        FCAConcept<O,A> first = new FCAConcept<>();

        //Add Concept (G,G') to the List
        first.setExtent(new ArrayList<>(context.getContextObjects()));
        first.setIntent(computePrimeOfObjects(context.getContextObjects(),context));
        concepts.add(first);

        //Iterate over each Attribute
        for(Attribute<O,A> a : context.getContextAttributes()){
            //Calculate the Prime for this Attribute
            //Use getAttributePrime Method; Hence the Parameter
            //Collections.singletonList.
            //Equivalently, one can use here a.getObjects();
            List<? extends ObjectAPI<O,A>> prime = computePrimeOfAttributes(Collections.singletonList(a),context);

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
                        concept.setIntent(context.getContextAttributes());
                    }else{
                        concept.setIntent(computePrimeOfObjects(intersection,context));
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
    public <T extends Context<O,A>,O,A> List<FCAConcept<O,A>> computeAllConcepts(List<List<? extends Attribute<O,A>>> closures, T context){
        //Create List of FCAConcepts that will be returned
        List<FCAConcept<O,A>> concepts = new ArrayList<>();
        //Go through List of closures and create a FCAConcept Object
        //for each closure. Also compute the prime and set the Extent accordingly.
        for(List<? extends Attribute<O,A>> list : closures){
            FCAConcept<O,A> concept = new FCAConcept<>();
            concept.setIntent(list);
            concept.setExtent(computePrimeOfAttributes(list,context));
            concepts.add(concept);
        }
        //Return List of concepts
        return concepts;
    }

    /**
     * Computes all Closures of the Context.
     * Note: Currently only Attribute Closures are computed.
     */
    public <T extends Context<O,A>,O,A> List<List<Attribute<O,A>>> computeAllClosures(T context, boolean... print){
        //Setting the Optional Parameter to False
        boolean printBool = print.length >= 1 && print[0];
        //Create List of Lists of Attributes (Closures) to be returned
        List<List<Attribute<O,A>>> closures = new ArrayList<>();
        //Set the lectically first Set
        IndexedList<Attribute<O,A>> A = firstClosure(context);
        if(printBool)System.out.println("CLOSURES:");
        while(A != null){
            //Print (Optional)
            if(printBool)System.out.println(A.getIndexedList().stream().map(Pair::getLeft).map(Attribute::getAttributeID).collect(Collectors.toList()));
            //Add A to the List to be returned
            closures.add(A.getIndexedList().stream().map(Pair::getLeft).collect(Collectors.toList()));
            A = nextClosure(A, context);
        }
        //Return the List of all Closures
        return closures;
    }

    /**
     * Computes the first closed set, i.e., ∅''.
     * @return First closed set as IndexedList
     */
    public static <T extends Context<O,A>,O,A> IndexedList<Attribute<O,A>> firstClosure(T context){
        //Create Indexed Attribute List of the Context
        IndexedList<Attribute<O,A>> index = new IndexedList<>(context.getContextAttributes());
        //Create a new IndexedList
        IndexedList<Attribute<O,A>> closure = new IndexedList<>();
        //Go through each Attribute of ∅'' and add the corresponding Pair to the
        //newly created IndexedList
        List<Attribute<O,A>> primeOfObjects = computePrimeOfObjects(context.getContextObjects(),context);
        for(Attribute<O,A> attr : primeOfObjects){
            closure.add(index.getPair(index.getIndex(attr)));
        }
        //return ∅''
        return closure;
    }

    /**
     * Computes the lectically next closed set of the given first parameter.
     * @param next The List from which the next closed set should be computed.
     * @param indexed The Indexed Attribute List of the Context.
     * @return IndexedList of the next closed set.
     */
    private <T extends Context<O,A>,O,A> IndexedList<Attribute<O,A>> nextClosure(IndexedList<Attribute<O,A>> next, T context){
        //Create Indexed Attribute List of the Context
        IndexedList<Attribute<O,A>> indexed = new IndexedList<>(context.getContextAttributes());
        //Go through List of all Attributes in reverse Order
        //Provide listIterator Parameter with size of the List to get a pointer
        //to the end of the list.
        ListIterator<Pair<Attribute<O,A>, Integer>> iterator = indexed.getIndexedList().listIterator(indexed.getIndexedList().size());
        while(iterator.hasPrevious()){
            //Get the previous Pair of Attribute and Index
            Pair<Attribute<O,A>, Integer> previous = iterator.previous();
            //If the Attribute is contained in next remove it
            if(next.contains(previous.getLeft())){
                next.remove(previous.getLeft());
            }else{
                //Create union of the current List and the previous Attribute
                IndexedList<Attribute<O,A>> union = new IndexedList<>();
                union.add(previous);
                union.addAll(next.getIndexedList());
                //Compute the double Prime of the union and save it
                List<Attribute<O,A>> B = computePrimeOfObjects(computePrimeOfAttributes(union.getObjects(),context),context);
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
                    List<Attribute<O,A>> doublePrime = computePrimeOfObjects(computePrimeOfAttributes(union.getObjects(),context),context);
                    for(Attribute<O,A> a : doublePrime ){
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
    public <O,A> List<Attribute<O,A>> computeLinClosure(List<Attribute<O,A>> attributes, List<FCAImplication<O,A>> implications){

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
    public <O,A,T extends Context<O,A>> List<FCAImplication<O,A>> computeStemBase(T context){
        //Create IndexedList of the Attributes of the Context
        IndexedList<Attribute<O,A>> indexedAttributes = new IndexedList<>(context.getContextAttributes());
        //Create new List and set it to the closure of the empty set
        List<Attribute<O,A>> A = firstClosure(context).getObjects();
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
            List<Attribute<O,A>> closureA = computePrimeOfObjects(computePrimeOfAttributes(A,context),context);
            if(!A.containsAll(closureA) && A.size()!=closureA.size()){
                //Check if the size of the Closure is not equal the size of all Attributes
                //If it is remove all Attribute from the Conclusion that are contained in the premise
                if(closureA.size()!=context.getContextAttributes().size())closureA.removeAll(A);
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





}
