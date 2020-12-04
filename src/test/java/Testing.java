import fca.FCAFormalContext;
import fca.FCAObject;
import utils.ContextHelper;
import utils.FCAOutputWriter;

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
        //FCAFormalContext<Integer,Integer> exampleContext = new FCAFormalContext<>(Integer.class,Integer.class);

        //They do not have to be of the same type
        //FCAFormalContext<Character,Double> exampleContext2 = new FCAFormalContext<>();

        //Display Crosstable on Console and Write output to File
        FCAOutputWriter<String,String> o = new FCAOutputWriter<>();
        //o.printToConsole(context);
        //o.writeToFile(context,"context_output.txt");

        //Option to delete file from properties package
        //PropertyIO p = new PropertyIO();
        //p.deleteFile("test.txt");

        //Create new TestContext
        FCAFormalContext<String,String> testContext = new FCAFormalContext<String,String>(){};

        //Create Objects and provide them with Attributes
        FCAObject<String,String> ob1 = new FCAObject<>("Q1");
        FCAObject<String,String> ob2 = new FCAObject<>("Q2");
        FCAObject<String,String> ob3 = new FCAObject<>("Q3");

        ob1.addAttribute("b");
        ob1.addAttribute("d");


        ob2.addAttribute("e");
        ob2.addAttribute("b");

        ob3.addAttribute("c");
        //ob3.addAttribute("b");

        //Add them to the Context;
        //Note: If an Attribute of an Object is not present in the
        //Attribute List of the Context, a new FCAAttribute Object is created
        //and added to the Attribute List of the Context.
        testContext.addFCAObject(ob1);
        testContext.addFCAObject(ob2);
        testContext.addFCAObject(ob3);

        List<String> ID = new ArrayList<>();
        ID.add("Q1");
        ID.add("Q2");
        //List<Attribute<String,String>> ab = testContext.Prime(testContext.getContextObjects(), ObjectAPI.class);

        FCAObject<String,String> ob4 = new FCAObject<>("Q4");
        ob4.addAttribute("a");
        ob4.addAttribute("c");
        ob4.addAttribute("b");
        testContext.addFCAObject(ob4);
        FCAObject<String,String> ob5 = new FCAObject<>("Q5");
        ob5.addAttribute("d");
        //ob5.addAttribute("b");
        FCAObject<String,String> ob6 = new FCAObject<>("Q6");
        ob6.addAttribute("b");
        ob6.addAttribute("c");
        FCAObject<String,String> ob7 = new FCAObject<>("Q7");
        ob7.addAttribute("e");
        //ob7.addAttribute("b");

        testContext.addFCAObject(ob5);
        testContext.addFCAObject(ob6);
        testContext.addFCAObject(ob7);


        //Print Context and all Concepts of TestContext
        o.printCrosstableToConsole(testContext);
        o.writeCrosstableToFile(testContext,"test_context.txt");

        o.printConceptsToConsole(testContext);
        o.writeConceptsToFile(testContext,"test_concepts.txt");

        o.printStemBaseToConsole(testContext);
        o.writeStemBaseToFile(testContext,"test_stembase.txt");

        //List<List<NEWAtt<String,String>>> l = testContext.computeAllClosures();

        o.printCrosstableToConsole(context);
        o.printConceptsToConsole(context);
        o.printStemBaseToConsole(context);

    }
}
