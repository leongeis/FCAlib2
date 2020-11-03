package utils;

import utils.exceptions.NoPropertiesDefinedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class used for storing the specified properties (attributes),
 * which are then used for further querying. The input for the properties
 * can either be user input or read from a file. New files can be created in the
 * properties package. See existing files for formatting.
 * @author Leon Geis
 */
public class PropertySet {

    /**
     * The set of all properties, which are used for querying.
     */
    private Set<String> properties;

    /**
     * List of all file names in the properties directory.
     */
    private List<String> fileNames;

    /**
     * Default Constructor.
     */
    public PropertySet() throws NoPropertiesDefinedException {
        this.properties = new HashSet<>();
        getFiles();
    }

    /**
     * Constructor, which takes a List of properties (Strings)
     * as an argument.
     * @param prop List of properties.
     */
    public PropertySet(List<String> prop) throws NoPropertiesDefinedException {
        this.properties = new HashSet<>();
        this.properties.addAll(prop);
        getFiles();
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

    /**
     * Read a List of Properties from file. If no parameter is given
     * the user can choose from a list of files.
     * @param name String of the file name
     */
    public void readFromFile(String name){
        //Initialize file String with path
        String fileName ="src/main/java/utils/properties/";
        //If no file name is given, let user choose a file
        if(name==null){
            fileName=fileName+readFromUser();
        }else {
            fileName=fileName+name;
        }
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

    /**
     * Retrieves the list of all files currently present in the properties package.
     * @throws NoPropertiesDefinedException When no file is found in the properties package.
     */
    private void getFiles() throws NoPropertiesDefinedException {
        //Define file path
        File file = new File("src/main/java/utils/properties/");
        //Create new String Array containing all files of this directory
        String[] fileNames = file.list();
        //Check if the directory contains a file
        if(fileNames==null) throw new NoPropertiesDefinedException("No Property file found!");
        //Add files to PropertySet file list
        this.fileNames = Arrays.asList(fileNames.clone());
    }

    /**
     * User can choose from a List of files.
     */
    public String readFromUser(){
        System.out.println("No Property File specified! Which Property File should be used?");
        for (int i = 0; i < this.fileNames.size(); i++) {
            System.out.println("("+(i+1)+")"+this.fileNames.get(i));
        }
        System.out.println("Enter Number of desired File:");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        return this.fileNames.get(input-1);
    }

    /**
     * Get size of the property set.
     * @return size as type int.
     */
    public int getSize(){
        return this.properties.size();
    }


}
