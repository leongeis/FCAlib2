package wikidata;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//TODO: IMPLEMENT FOR CHOOSING PROPERTIES(ATTRIBUTES) FOR GIVEN CONTEXT

//EXAMPLE PROPERTIES FROM WIKIDATA: {mother,father,spouse,sibling,cause of death}

/**
 * This class used for storing the specified properties (attributes),
 * which are then used for further querying. The input for the properties
 * can either be user input or read from a file.
 * @author Leon Geis
 */
public class PropertySet implements Set {

    /**
     * The set of all properties, which are used for querying.
     */
    private Set<String> properties;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }


}
