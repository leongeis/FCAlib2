package fca;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a Concept of a Context. A
 * Concept is a pair (A,B), where A is called
 * extent and B intent of this concept.
 * A concept always fulfills: A'= B and B'= A, with
 * A⊆G and B⊆M. (G set of Objects and M set of Attributes)
 * @param <O> Type of the Objects
 * @param <A> Type of the Attributes
 * @author Leon Geis
 */
public class FCAConcept<O,A> {

    /**
     * List of Objects of an Concept.
     */
    private List<FCAObject<O,A>> extent;

    /**
     * List of Attributes of an Concept
     */
    private List<FCAAttribute<O,A>> intent;

    /**
     * Constructor of the Class.
     */
    public FCAConcept(){
        this.extent = new ArrayList<>();
        this.intent = new ArrayList<>();
    }

    /**
     * Get all Objects of this Concept.
     * @return List of FCAObjects
     */
    public List<FCAObject<O,A>> getExtent() {
        return extent;
    }

    /**
     * Set the Objects of an Concept.
     * @param extent New List of Objects for this Concept.
     */
    public void setExtent(List<FCAObject<O, A>> extent) {
        this.extent = extent;
    }

    /**
     * Get all Attributes of this Concept.
     * @return List of FCAAttributes
     */
    public List<FCAAttribute<O,A>> getIntent() {
        return intent;
    }

    /**
     * Set the Attributes of this Concept.
     * @param intent New List of Attributes for this Concept.
     */
    public void setIntent(List<FCAAttribute<O, A>> intent) {
        this.intent = intent;
    }

    //TODO FORMATTING
    /**
     * Prints first the extent of this Concept
     * and afterward the intent.
     */
    public void printConcept(){
        System.out.print("({");
        if(this.extent != null) {
            for (int i = 0; i < this.extent.size(); i++) {
                if (i + 1 == this.extent.size()) System.out.print(this.extent.get(i).getObjectID() + "};{");
                else System.out.print(this.extent.get(i).getObjectID() + ",");
            }
        }else{
            System.out.print("};");
        }
        if(this.intent != null) {
            for (int i = 0; i < this.intent.size(); i++) {
                if (i + 1 == this.intent.size()) System.out.println(this.intent.get(i).getAttributeID() + "})");
                else System.out.print(this.intent.get(i).getAttributeID() + ",");
            }
        }else{
            System.out.print("})");
        }
    }

}
