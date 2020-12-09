package utils;

import api.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used only for Displaying/Writing to File.
 * @author Leon Geis
 */
public class FCAOutputWriter implements OutputWriter {

    /**
     * The fixed file path the files are created in.
     */
    private static final String filepath = "src/main/java/utils/output/";

    /**
     * Displays the Crosstable of the current Context on
     * the console.
     */
    @Override
    public <T extends Context<O,A>,O,A> void printCrosstableToConsole(T context){
        //Headline of the Output
        System.out.println("Crosstable of the context with ID: "+context.getContextID());
        System.out.println("The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute");
        //Initial space for formatting purposes
        //Using the Stream API to receive the longest ID (as a String) of an Object
        int length = Collections.max(context.getContextObjects(),Comparator.comparing(object -> object.getObjectID().toString().length())).getObjectID().toString().length()+1;
        for (int i = 0; i < length; i++) {
            System.out.print(" ");
        }
        //Print each Attribute name
        for(Attribute<O,A> a : context.getContextAttributes()){
            System.out.print(a.getAttributeID()+" ");
        }
        //Newline
        System.out.println();
        //Go through each Object and print the corresponding Attributes
        for(ObjectAPI<O,A> o : context.getContextObjects()){
            //Get the length of the name (formatting purposes)
            int nameLength = o.getObjectID().toString().length();
            //Print name of Object
            System.out.print(o.getObjectID());
            //Printing whitespaces for formatting
            while(nameLength<length){
                System.out.print(" ");
                nameLength++;
            }
            //Go through each Attribute and check if Object has Attribute
            //If it does Print X else -
            for(Attribute<O,A> a : context.getContextAttributes()){
                if(o.getDualEntities().contains(a.getAttributeID())){
                    System.out.print("X ");
                }else {
                    System.out.print("- ");
                }
                //Printing whitespaces for formatting of the table
                for (int i = 0; i < a.getAttributeID().toString().length()-1; i++) {
                    System.out.print(" ");
                }
            }
            //Newline for each Object
            System.out.println();
        }
    }


    /**
     * Writes the Crosstable to a file. The file name
     * is specified via a String parameter ("example.txt")
     */
    @Override
    public <T extends Context<O,A>,O,A> void writeCrosstableToFile(T context, String fileName){
        //Append file name to path
        String name = filepath+fileName;
        //Print to Console
        System.out.println("Writing to File: "+name);
        try (Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), StandardCharsets.UTF_8))){
            //Headline of the Output
            fileWriter.write("Crosstable of the context with ID: "+context.getContextID()+"\n");
            fileWriter.write("The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute\n");
            //Initial space for formatting purposes
            //Using the Stream API to receive the longest ID (as a String) of an Object
            int length = Collections.max(context.getContextObjects(),Comparator.comparing(object -> object.getObjectID().toString().length())).getObjectID().toString().length()+1;
            for (int i = 0; i < length; i++) {
                fileWriter.write(" ");
            }
            //Print each Attribute name
            for(Attribute<O,A> a : context.getContextAttributes()){
                fileWriter.write(a.getAttributeID()+" ");
            }
            //Newline
            fileWriter.write("\n");
            //Go through each Object and print the corresponding Attributes
            for(ObjectAPI<O,A> o : context.getContextObjects()){
                //Get the length of the name (formatting purposes)
                int nameLength = o.getObjectID().toString().length();
                //Print name of Object
                fileWriter.write(o.getObjectID().toString());
                //Printing whitespaces for formatting
                while(nameLength<length){
                    fileWriter.write(" ");
                    nameLength++;
                }
                //Go through each Attribute and check if Object has Attribute
                //If it does Print X else -
                for(Attribute<O,A> a : context.getContextAttributes()){
                    if(o.getDualEntities().contains(a.getAttributeID())){
                        fileWriter.write("X ");
                    }else {
                        fileWriter.write("- ");
                    }
                    //Printing whitespaces for formatting of the table
                    for (int i = 0; i < a.getAttributeID().toString().length()-1; i++) {
                        fileWriter.write(" ");
                    }
                }
                //Newline for each Object
                fileWriter.write("\n");
            }
            //Print Message to Console
            System.out.println("Writing Successful!");
        //Catch Exception
        }catch (Exception e){
            //Print Error to Console
            System.out.println("Writing not successful!");
            e.printStackTrace();
        }

    }

    /**
     * Displays all Concepts of the current Context on
     * the console. It uses nextClosure to compute all concepts.
     * @param context The Context Object.
     */
    @Override
    public <T extends Context<O,A>,O,A> void printConceptsToConsole(T context) {
        System.out.println("Concepts of the context with ID: "+context.getContextID());
        System.out.println("Concepts (A,B) with A⊆G and B⊆M. G is the set of all Objects and M the set of all Attributes.");
        List<List<Attribute<O,A>>> closures = Computation.computeAllClosures(context);
        List<Concept<O,A>> concepts = Computation.computeAllConcepts(closures,context);
        for(Concept<O,A> concept : concepts){
            concept.printConcept();
        }
    }

    /**
     * Writes all Concepts of the given Context to the specific file.
     * @param context The Context from which all Concepts will be computed
     * @param name The Name of the File ("example.txt")
     * Note: Uses NextClosure to compute all Concepts.
     */
    @Override
    public <T extends Context<O,A>,O,A> void writeConceptsToFile(T context, String name) {
        //Append the file name
        String fileName = filepath+name;
        //Print to Console
        System.out.println("Writing to File: "+fileName);
        //Create Writer Object
        try(Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))){
            //Write Headline of the file
            fileWriter.write("Concepts of the context with ID: "+context.getContextID()+"\n");
            fileWriter.write("Concepts (A,B) with A⊆G and B⊆M. G is the set of all Objects and M the set of all Attributes.\n");
            //Get the List of all Concepts
            List<Concept<O,A>> concepts = Computation.computeAllConcepts(Computation.computeAllClosures(context),context);
            //Go through each concept and display the extent and the intent
            for(Concept<O,A> concept : concepts){
                fileWriter.write("CONCEPT:"+concept.getExtent().stream().map(ObjectAPI::getObjectID).collect(Collectors.toList())+";");
                fileWriter.write(concept.getIntent().stream().map(Attribute::getAttributeID).collect(Collectors.toList())+"\n");
            }
            //Display Success Message
            System.out.println("Writing Successful!");
        //Catch Exception and Display Message
        }catch (Exception e){
            System.out.println("Writing not Successful!");
            e.printStackTrace();
        }
    }

    /**
     * Displays all Implications of the Stem Base of the Context
     * on the console.
     * @param context The Context from which the Stem Base will be computed
     */
    public <T extends Context<O,A>,O,A> void printStemBaseToConsole(T context){
        System.out.println("Stem Base of the context with ID: "+context.getContextID());
        System.out.println("Implications with the premise on the left and the conclusion on the right.");
        for(Implication<O,A> impl : Computation.computeStemBase(context)){
            System.out.print("IMPLICATION:"+impl.toString()+"\n");
        }
    }
    /**
     * Writes all Implications of the Stem Base of the given Context to the specific file.
     * @param context The Context from the Stem Base will be computed.
     * @param name The Name of the File ("example.txt")
     */
    public <T extends Context<O,A>,O,A> void writeStemBaseToFile(T context, String name){
        //Append the file name
        String fileName = filepath+name;
        //Print to Console
        System.out.println("Writing to File: "+fileName);
        //Create Writer Object
        try(Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))){
            //Write Headline of the file
            fileWriter.write("Stem Base of the context with ID: "+context.getContextID()+"\n");
            fileWriter.write("Implications with the premise on the left and the conclusion on the right.\n");
            //Go through each Implication and display the premise and the conclusion
            for(Implication<O,A> impl : Computation.computeStemBase(context)){
                fileWriter.write("IMPLICATION:"+impl.toString()+"\n");
            }
            //Display Success Message
            System.out.println("Writing Successful!");
            //Catch Exception and Display Message
        }catch (Exception e){
            System.out.println("Writing not Successful!");
            e.printStackTrace();
        }
    }



}
