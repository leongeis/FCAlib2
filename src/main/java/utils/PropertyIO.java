package utils;

import utils.exceptions.NoPropertiesDefinedException;
import wikidata.SPARQLQueryBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
public class PropertyIO {

    /**
     * The set of all properties, which are used for querying.
     */
    private Set<String> properties;

    /**
     * List of all file names in the properties directory.
     */
    private List<String> fileNames;

    /**
     * Path of the files.
     */
    private static String filePath="src/main/java/utils/properties/";

    /**
     * Default Constructor.
     */
    public PropertyIO() throws NoPropertiesDefinedException {
        this.properties = new HashSet<>();
        getFiles();
    }

    /**
     * Constructor, which takes a List of properties (Strings)
     * as an argument.
     * @param prop List of properties.
     */
    public PropertyIO(List<String> prop) throws NoPropertiesDefinedException {
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
        String fileName =filePath;
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
        File file = new File(filePath);
        //Create new String Array containing all files of this directory
        String[] fileNames = file.list();
        //Check if the directory contains a file
        if(fileNames==null) throw new NoPropertiesDefinedException("No Property file found!");
        //Add files to PropertyIO file list
        this.fileNames = Arrays.asList(fileNames.clone());
    }

    /**
     * User can choose from a List of files.
     */
    public String readFromUser(){
        System.out.println("No Property File specified! Which Property File should be used?");
        int i;
        for (i = 0; i < this.fileNames.size(); i++) {
            System.out.println("("+(i+1)+") Choose: "+this.fileNames.get(i));
        }
        System.out.println("("+(i+1)+") Create a new File.");
        System.out.println("Enter Number of desired Action:");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        sc.nextLine();
        if(input==i+1){
            try {
                return createFile(sc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.fileNames.get(input-1);
    }

    /**
     * Get size of the property set.
     * @return size as type int.
     */
    public int getSize(){
        return this.properties.size();
    }

    //TODO FORMAT CHECKING
    /**
     * Creates a new File and lets the user specify own Properties.
     * @param s Scanner Object
     * @return Name of the created file as String
     * @throws IOException When error occurred during file creation.
     */
    public String createFile(Scanner s) throws IOException {
        //Display Format for Input
        System.out.println("Format: P?,P?,P?,P?,P?");
        System.out.println("Where ? has to be replaced by an arbitrary Integer.");
        System.out.println("Enter desired Properties (Amount of Properties should not be above "+SPARQLQueryBuilder.getPropertyCount()+"): ");
        //Get next Line and save it as a String
        String input=s.nextLine();
        //Display Msg on Console
        System.out.println("Enter file Name:");
        //Get next Line and save it as String
        String name=s.nextLine();
        //Append file with .txt ending
        name=name+".txt";
        //Create BufferedWriter Object and provide it with the specified file name
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+name));
        //Write Properties to new File
        writer.write(input);
        //Close Write Object
        writer.close();
        //Return name of new file
        return name;
    }

    /**
     * Deletes a File from the properties package.
     * @param name Name of the file as String.
     */
    public void deleteFile(String name){
        //Display Info Messages
        System.out.println("Are you sure to delete "+name+"?");
        System.out.print("yes/no[y/n]:");
        //Create Scanner Object
        Scanner sc = new Scanner(System.in);
        //Read next Char (String)
        String in = sc.next();
        //Check Input and either delete the given file
        //or print error message.
        if(in.equals("y")||in.equals("yes")) {
            File f = new File(filePath + name);
            if (f.delete()) {
                System.out.println("File deleted!");
            } else {
                System.out.println("Failed to delete File!");
            }
        }else if(in.equals("n")||in.equals("no")){
            System.out.println("Canceled!");
        }
    }

}
