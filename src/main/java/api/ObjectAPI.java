package api;

/**
 * Interface describing a single Object.
 * @param <O> Type of the Object itself.
 * @param <A> Type of the Attributes this Object holds.
 * @author Leon Geis
 */
public interface ObjectAPI<O,A> extends ClosureOperator {

    public O getObjectID();
    public void setObjectID(O objectID);
    public void addAttribute(A attribute);

}
