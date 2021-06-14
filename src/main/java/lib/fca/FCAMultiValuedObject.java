package lib.fca;

import api.fca.MultiValuedAttribute;
import api.fca.MultiValuedObject;
import lib.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//TODO JAVADOC
public class FCAMultiValuedObject<O,A,V> implements MultiValuedObject<O,A,V> {

    private O objectID;

    private List<Pair<MultiValuedAttribute<O,A,V>, List<V>>> attributes;

    public FCAMultiValuedObject(){
        this.objectID = null;
        this.attributes = new ArrayList<>();
    }

    public FCAMultiValuedObject(O o){
        this.objectID = o;
        this.attributes = new ArrayList<>();
    }

    public boolean addAttribute(MultiValuedAttribute<O,A,V> a, List<V> v){
        if(containsAttribute(a)){
            return false;
        }else{
            this.attributes.add(new Pair<>(a,v));
            return true;
        }
    }

    public O getObjectID(){
        return this.objectID;
    }

    public void setObjectID(O o){
        this.objectID = o;
    }

    public boolean containsAttribute(MultiValuedAttribute<O,A,V> a){
        if(this.attributes.stream().map(Pair::getLeft).collect(Collectors.toList()).stream().map(MultiValuedAttribute::getAttributeID).
                collect(Collectors.toList()).contains(a.getAttributeID())){
            return true;
        }else{
            return false;
        }
    }

    public Pair<MultiValuedAttribute<O,A,V>,List<V>> getAttribute(MultiValuedAttribute<O,A,V> a){
        if(containsAttribute(a)){
            int index = this.attributes.stream().map(Pair::getLeft).collect(Collectors.toList()).
                    stream().map(MultiValuedAttribute::getAttributeID).collect(Collectors.toList()).indexOf(a.getAttributeID());
            return this.attributes.get(index);
        }else{
            return null;
        }
    }

    public List<Pair<MultiValuedAttribute<O,A,V>,List<V>>> getDualEntities() {
        return this.attributes;
    }
}
