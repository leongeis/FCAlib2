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

/**
 * Class used only for Displaying/Writing to File.
 * @author Leon Geis
 */
//TODO REWORK GENERICS
public class FCAOutputWriter implements OutputWriter<FCAFormalContext<FCAObject,FCAAttribute>> {

    /**TODO: FORMATTING
     * Displays the Crosstable of the current Context on
     * the console.
     */
    @Override
    public void printToConsole(FCAFormalContext<FCAObject,FCAAttribute> c){
        //Headline of the Output
        System.out.println("The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute");
        //Initial space for formatting purposes
        System.out.print("          ");
        //Print each Attribute name
        for(FCAAttribute a : c.getContextAttributes()){
            System.out.print(a.getName()+" ");
        }
        //Newline
        System.out.println();
        //Go through each Object and print the corresponding Attributes
        for(FCAObject o : c.getContextObjects()){
            //Get the length of the name (formatting purposes)
            int nameLength = o.getObjectName().length();
            //Print name of Object
            System.out.print(o.getObjectName());
            //Printing whitespaces for formatting
            while(nameLength<10){
                System.out.print(" ");
                nameLength++;
            }
            //Go through each Attribute and check if Object has Attribute
            //If it does Print X else -
            for(FCAAttribute a : c.getContextAttributes()){
                if(o.getAttributes().contains(a)){
                    System.out.print("X ");
                }else {
                    System.out.print("- ");
                }
                //Printing whitespaces for formatting of the table
                for (int i = 0; i < a.getName().length()-1; i++) {
                    System.out.print(" ");
                }
            }
            //Newline for each Object
            System.out.println();
        }
    }

    /**TODO: FORMATTING / GENERIC FILE WRITING
     * Writes the Crosstable to a file. The file path
     * is specified via a String parameter ("~/example.txt")
     */
    @Override
    public void writeToFile(FCAFormalContext<FCAObject,FCAAttribute> c, String fileName){
        //Append file name to path
        String name = "src/main/java/utils/"+fileName;
        //Print to Console
        System.out.println("Writing to File: "+name);
        try (Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name), StandardCharsets.UTF_8))){
            //Headline of the Output
            fileWriter.write("The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute\n");
            //Initial space for formatting purposes
            fileWriter.write("          ");
            //Print each Attribute name
            for(FCAAttribute a : c.getContextAttributes()){
                fileWriter.write(a.getName()+" ");
            }
            //Newline
            fileWriter.write("\n");
            //Go through each Object and print the corresponding Attributes
            for(FCAObject o : c.getContextObjects()){
                //Get the length of the name (formatting purposes)
                int nameLength = o.getObjectName().length();
                //Print name of Object
                fileWriter.write(o.getObjectName());
                //Printing whitespaces for formatting
                while(nameLength<10){
                    fileWriter.write(" ");
                    nameLength++;
                }
                //Go through each Attribute and check if Object has Attribute
                //If it does Print X else -
                for(FCAAttribute a : c.getContextAttributes()){
                    if(o.getAttributes().contains(a)){
                        fileWriter.write("X ");
                    }else {
                        fileWriter.write("- ");
                    }
                    //Printing whitespaces for formatting of the table
                    for (int i = 0; i < a.getName().length()-1; i++) {
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
