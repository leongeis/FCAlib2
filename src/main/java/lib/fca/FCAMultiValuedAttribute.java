package lib.fca;

import api.fca.MultiValuedAttribute;
import api.fca.MultiValuedObject;
import lib.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//TODO JAVADOC
public class FCAMultiValuedAttribute<O,A,V> implements MultiValuedAttribute<O,A,V> {

    private A attributeID;

    private List<Pair<MultiValuedObject<O,A,V>, List<V>>> objects;

    public FCAMultiValuedAttribute(){
        this.attributeID = null;
        this.objects = new ArrayList<>();
    }

    public FCAMultiValuedAttribute(A a){
        this.attributeID = a;
        this.objects = new ArrayList<>();
    }

    public A getAttributeID(){
        return this.attributeID;
    }
    public void setAttributeID(A a){
        this.attributeID = a;
    }

    public boolean addObject(MultiValuedObject<O,A,V> o, List<V> v){
        //Check if Object is already contained
        if(containsObject(o)){
            return false;
        }else{
            this.objects.add(new Pair<>(o,v));
            return true;
        }
    }

    public Pair<MultiValuedObject<O,A,V>, List<V>> getObject(MultiValuedObject<O,A,V> o){
        if(containsObject(o)){
            int index = this.objects.stream().map(Pair::getLeft).collect(Collectors.toList()).stream().map(MultiValuedObject::getObjectID)
            .collect(Collectors.toList()).indexOf(o.getObjectID());
            return this.objects.get(index);
        }else{
            return null;
        }
    }

    public boolean containsObject(MultiValuedObject<O,A,V> o){
        if(this.objects.stream().map(Pair::getLeft).collect(Collectors.toList()).stream().map(MultiValuedObject::getObjectID).
                collect(Collectors.toList()).contains(o.getObjectID())){
            return true;
        }else{
            return false;
        }
    }

    public List<Pair<MultiValuedObject<O,A,V>, List<V>>> getDualEntities() {
        return this.objects;
    }
}
