package api.fca;

import lib.utils.Pair;

import java.util.List;
//TODO JAVADOC
public interface MultiValuedObject<O,A,V> extends ClosureOperator{

    public O getObjectID();

    void setObjectID(O o);

    boolean addAttribute(MultiValuedAttribute<O,A,V> a, List<V> v);

    boolean containsAttribute(MultiValuedAttribute<O,A,V> a);

    Pair<MultiValuedAttribute<O,A,V>,List<V>> getAttribute(MultiValuedAttribute<O,A,V> a);

    List<Pair<MultiValuedAttribute<O,A,V>,List<V>>> getDualEntities();

}
