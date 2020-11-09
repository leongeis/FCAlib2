import fca.FCAAttribute;
import fca.FCAConcept;
import fca.FCAFormalContext;
import fca.FCAObject;
import utils.FCAOutputWriter;
import utils.ContextHelper;

import java.util.ArrayList;
import java.util.List;


/*For further information on RDF4J connections:
*https://rdf4j.org/documentation/programming/repository/#using-a-repository-repositoryconnections
*(Accessed: 20.10.20 14:34 (MESZ))
 */

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) {

        //Create a FCAFormalContext Object and initialize it with Data from Wikidata
        //first parameter: a SPARQLQueryBuilder Object is used for generating a query
        //making the second parameter obsolete. Hence null.
        //third parameter: specifying a file name, the properties are read from. When null is provided,
        //the user can choose from a list of files.

        //This approach always returns an object with both types as String.
        //Thus one can split this line to enable a more generic approach to create a FCAFormalContext Object.

        FCAFormalContext<String,String> context = ContextHelper.createContextFromWikidata(true,null,"family_properties.txt");

        //Here both Objects Attributes are of type Integer
        FCAFormalContext<Integer,Integer> exampleContext = new FCAFormalContext<>();

        //They do not have to be of the same type
        FCAFormalContext<Character,Double> exampleContext2 = new FCAFormalContext<>();

        //Display Crosstable on Console and Write output to File
        FCAOutputWriter<String,String> o = new FCAOutputWriter<>();
        o.printToConsole(context);
        o.writeToFile(context,"context_output.txt");

        //Option to delete file from properties package
        //PropertyIO p = new PropertyIO();
        //p.deleteFile("test.txt");

        //Create new TestContext
        FCAFormalContext<String,String> testContext = new FCAFormalContext<>();

        //Create Objects and provide them with Attributes
        FCAObject<String,String> ob1 = new FCAObject<>("Q1");
        FCAObject<String,String> ob2 = new FCAObject<>("Q2");
        FCAObject<String,String> ob3 = new FCAObject<>("Q3");

        ob1.addAttribute("a");
        ob1.addAttribute("b");
        ob1.addAttribute("c");
        //ob1.addAttribute("d");

        ob2.addAttribute("c");
        ob2.addAttribute("b");

        ob3.addAttribute("c");
        ob3.addAttribute("d");
        ob3.addAttribute("b");

        //Add them to the Context;
        //Note: If an Attribute of an Object is not present in the
        //Attribute List of the Context, a new FCAAttribute Object is created
        //and added to the Attribute List of the Context.
        testContext.addFCAObject(ob1);
        testContext.addFCAObject(ob2);
        testContext.addFCAObject(ob3);

        FCAObject<String,String> ob4 = new FCAObject<>("Q4");
        ob4.addAttribute("b");
        ob4.addAttribute("c");
        testContext.addFCAObject(ob4);
        o.printToConsole(testContext);

        //Print all Concepts of the Context
        List<FCAConcept<String,String>> c = context.computeAllConcepts();
        for(FCAConcept<String,String> s : c){
            s.printConcept();
        }
    }
}
