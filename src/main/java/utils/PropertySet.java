package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * Default Constructor.
     */
    public PropertySet(){
        this.properties = new HashSet<>();
    }

    /**
     * Constructor, which takes a List of properties (Strings)
     * as an argument.
     * @param prop List of properties.
     */
    public PropertySet(List<String> prop){
        this.properties = new HashSet<>();
        this.properties.addAll(prop);
    }

    /**
     * Get the Set of properties.
     * @return Set of Strings of all properties.
     */
    public Set<String> getProperties(){
        return this.properties;
    }

    /**
     * Add a property to the set.
     * @param prop String of the property.
     */
    public void addProperty(String prop){
        this.properties.add(prop);
    }

    /** TODO OPEN EXPLORER TO CHOOSE FILE / GENERIC FILE ACCESS
     * Read a List of Properties from file wikidata_properties.txt
     */
    public void readFromFile(){
        String fileName = "src/main/java/utils/wikidata_properties.txt";
        //Save Content of file as String
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Split String at each comma
        //Assume file is not empty:
        assert content != null;
        String[] split = content.split(",");
        //Overwrite existing Properties
        this.properties = new HashSet<>();
        //Add each Property to property List
        for(String s : split){
            this.addProperty(s);
        }

    }

    /** TODO
     * Read a List of Properties from User Input.
     */
    public void readFromUser(){

    }

    /**
     * Get size of the property set.
     * @return size as type int.
     */
    public int getSize(){
        return this.properties.size();
    }


}
