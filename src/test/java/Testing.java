import api.fca.Computation;
import api.fca.Context;
import api.fca.Implication;
import api.fca.ObjectAPI;
import api.utils.ContextHelper;
import api.utils.OutputPrinter;
import api.utils.Performance;
import api.utils.SPARQLQueryGenerator;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;

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
        //first parameter: a WikidataQueryBuilder Object is used for generating a query
        //making the second parameter obsolete. Hence null.
        //third parameter: specifying a file name, the properties are read from. When null is provided,
        //the user can choose from a list of files.

        //This approach always returns an object with both types as String.
        //Thus one can split this line to enable a more generic approach to create a FCAFormalContext Object.
        long a1 = Performance.setTimeStamp();
        Context<String,String> context = ContextHelper.createContextFromWikidata(true,null,"human_relationships.txt");
        long a2 = Performance.setTimeStamp();
        System.out.println("Creating a Context from Wikidata took: "+Performance.convertToFormat(Performance.getExecutionTime(a1,a2)));
        OutputPrinter.printCrosstableToConsole(context);
        //OutputPrinter.printConceptsToConsole(context);
        /*for(Implication<String,String> impl : Computation.computeStemBase(context)){
            System.out.println(impl.toString()+": "+Computation.computeImplicationSupport(impl,context));
        }*/

        //Here both Objects Attributes are of type Integer
        //FCAFormalContext<Integer,Integer> exampleContext = new FCAFormalContext<>(Integer.class,Integer.class);

        //They do not have to be of the same type
        //FCAFormalContext<Character,Double> exampleContext2 = new FCAFormalContext<>();

        //o.printToConsole(context);
        //o.writeToFile(context,"context_output.txt");

        //Option to delete file from properties package
        //PropertyIO p = new PropertyIO();
        //p.deleteFile("test.txt");
        List<String> props = new ArrayList<>();
        props.add("dbp:date");
        props.add("dbp:df");
        //System.out.println(SPARQLQueryGenerator.generateSelectQuery(props,10));
        SPARQLQueryGenerator.generatePropertyCheckQuery("dbr:Douglas_Adams",props);
        //Create new TestContext
        Context<String,String> testContext = new FCAFormalContext<String,String>(){};

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
        testContext.addObject(ob1);
        testContext.addObject(ob2);
        testContext.addObject(ob3);

        List<String> ID = new ArrayList<>();
        ID.add("Q1");
        ID.add("Q2");
        //List<Attribute<String,String>> ab = testContext.Prime(testContext.getContextObjects(), ObjectAPI.class);

        FCAObject<String,String> ob4 = new FCAObject<>("Q4");
        ob4.addAttribute("a");
        ob4.addAttribute("c");
        ob4.addAttribute("b");
        testContext.addObject(ob4);
        FCAObject<String,String> ob5 = new FCAObject<>("Q5");
        ob5.addAttribute("d");
        //ob5.addAttribute("b");
        FCAObject<String,String> ob6 = new FCAObject<>("Q6");
        ob6.addAttribute("b");
        ob6.addAttribute("c");
        FCAObject<String,String> ob7 = new FCAObject<>("Q7");
        ob7.addAttribute("e");
        //ob7.addAttribute("b");

        testContext.addObject(ob5);
        testContext.addObject(ob6);
        testContext.addObject(ob7);

        List<ObjectAPI<String,String>> tm = testContext.getContextObjects();
        List<ObjectAPI<Integer,Integer>> ab = new ArrayList<>();
        ab.add(new FCAObject<>(2));
        //testContext.getContextAttributes().add(new AttFCA<>());
        //List<PartObj<String,String>> pert = testContext.getContextObjects();

        //FCAFormalContext<Integer,Integer> context = new FCAFormalContext<Integer, Integer>() {};
        //context.createObject(2,FCAObject.class);
        List<Integer> test = new ArrayList<>();
        test.add(2);
        //List<ObjectAPI<Integer,Integer>> list = context.getObjects(test);
        /*for(ObjectAPI<Integer,Integer> a : list){
            System.out.println(a.getObjectID());
        }*/
        for(Implication<String,String> impl : Computation.computeStemBase(testContext)){
            System.out.println(impl.toString()+": "+Computation.computeImplicationSupport(impl,testContext));
        }
        //Print Context and all Concepts of TestContext
        OutputPrinter.printCrosstableToConsole(testContext);
        OutputPrinter.writeCrosstableToFile(testContext,"test_context.txt");

        OutputPrinter.printConceptsToConsole(testContext);
        OutputPrinter.writeConceptsToFile(testContext,"test_concepts.txt");

        OutputPrinter.printStemBaseToConsole(testContext);
        OutputPrinter.writeStemBaseToFile(testContext,"test_stembase.txt");

        /*OutputPrinter.printCrosstableToConsole(context);
        OutputPrinter.printConceptsToConsole(context);
        OutputPrinter.printStemBaseToConsole(context);*/
        /*for(Implication<String,String> impl : Computation.computeStemBase(context)){
            System.out.println(impl.toString()+": "+Computation.computeImplicationSupport(impl,context));
        }*/
        Computation.computePrimeOfObjects(testContext.getContextObjects(),testContext);
        Computation.computeAllConcepts(testContext);
        Computation.computeStemBase(testContext);

    }
}
