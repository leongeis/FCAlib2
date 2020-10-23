package wikidata;

import java.util.*;

//TODO: IMPLEMENT FOR CHOOSING PROPERTIES(ATTRIBUTES) FOR GIVEN CONTEXT

//EXAMPLE PROPERTIES FROM WIKIDATA: {mother,father,spouse,sibling,cause of death}

/**
 * This class used for storing the specified properties (attributes),
 * which are then used for further querying. The input for the properties
 * can either be user input or read from a file.
 * @author Leon Geis
 */
public class PropertySet {

    /**
     * The set of all properties, which are used for querying.
     */
    private Set<String> properties;

    public PropertySet(){
        this.properties = new HashSet<>();
    }

    public PropertySet(List<String> prop){
        this.properties = new HashSet<>();
        this.properties.addAll(prop);
    }

    public Set<String> getProperties(){
        return this.properties;
    }

    public void addProperty(String prop){
        this.properties.add(prop);
    }

    public void readFromFile(){

    }

    public void readFromUser(){

    }

    public int getSize(){
        return this.properties.size();
    }


}
