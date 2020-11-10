package utils;

import api.OutputWriter;
import fca.FCAAttribute;
import fca.FCAFormalContext;
import fca.FCAObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class used only for Displaying/Writing to File.
 * @author Leon Geis
 */
public class FCAOutputWriter<O,A> implements OutputWriter<FCAFormalContext<O,A>> {

    /**
     * Displays the Crosstable of the current Context on
     * the console.
     */
    @Override
    public void printToConsole(FCAFormalContext<O,A> c){
        //Headline of the Output
        System.out.println("The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute");
        //Initial space for formatting purposes
        //Using the Stream API to receive the longest ID (as a String) of an Object
        int length = Collections.max(c.getContextObjects(),Comparator.comparing(object -> object.getObjectID().toString().length())).getObjectID().toString().length()+1;
        for (int i = 0; i < length; i++) {
            System.out.print(" ");
        }
        //Print each Attribute name
        for(FCAAttribute<O,A> a : c.getContextAttributes()){
            System.out.print(a.getAttributeID()+" ");
        }
        //Newline
        System.out.println();
        //Go through each Object and print the corresponding Attributes
        for(fca.FCAObject<O,A> o : c.getContextObjects()){
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
            for(fca.FCAAttribute<O,A> a : c.getContextAttributes()){
                if(o.getAttributes().contains(a.getAttributeID())){
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
    public void writeToFile(FCAFormalContext<O,A> c, String fileName){
        //Append file name to path
        String name = "src/main/java/utils/"+fileName;
        //Print to Console
        System.out.println("Writing to File: "+name);
        try (Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), StandardCharsets.UTF_8))){
            //Headline of the Output
            fileWriter.write("The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute\n");
            //Initial space for formatting purposes
            //Using the Stream API to receive the longest ID (as a String) of an Object
            int length = Collections.max(c.getContextObjects(),Comparator.comparing(object -> object.getObjectID().toString().length())).getObjectID().toString().length()+1;
            for (int i = 0; i < length; i++) {
                fileWriter.write(" ");
            }
            //Print each Attribute name
            for(FCAAttribute<O,A> a : c.getContextAttributes()){
                fileWriter.write(a.getAttributeID()+" ");
            }
            //Newline
            fileWriter.write("\n");
            //Go through each Object and print the corresponding Attributes
            for(FCAObject<O,A> o : c.getContextObjects()){
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
                for(FCAAttribute<O,A> a : c.getContextAttributes()){
                    if(o.getAttributes().contains(a.getAttributeID())){
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

}
