import api.OutputWriter;
import fca.FCAAttribute;
import fca.FCAObject;
import fca.FormalContext;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import utils.FCAOutputWriter;
import utils.PropertySet;
import wikidata.SPARQLQueryBuilder;
import wikidata.WikidataExtraction;

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

        //Create new Object of type WikidataExtraction
        WikidataExtraction wa = new WikidataExtraction();
        //Establish a connection to the SPARQL Endpoint of Wikidata
        wa.establishConnection();

        //(Optional) Create a SPARQL query builder object
        SPARQLQueryBuilder builder = new SPARQLQueryBuilder();

        //(Optional) Set the Limit of the received Entities to 25 (Standard Limit is set to 10)
        builder.setLimit(25);

        //Create a List of Properties the queries will be based on
        /*List<String> p = new ArrayList<>();
        p.add("P509");
        p.add("P22");
        p.add("P25");
        p.add("P3373");
        p.add("P26"); */

        //Create a PropertySet object and read properties from file
        PropertySet prop = new PropertySet();
        //Read from wikidata_properties.txt
        prop.readFromFile();

        //(Optional) add Variables to the SPARQLQueryBuilder object
        builder.addVariable("item");
        builder.addVariable("itemLabel");

        //(Optional) Generate a SELECT Query based on the properties
        String query= null;
        try {
            query = builder.generateSelectQuery(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create a fca.FormalContext object, which stores the received objects/attributes (G/M)
        FormalContext c = new FormalContext();

        //Generate fca.FCAAttribute List
        for(String s : prop.getProperties()){
            c.addAttribute(new FCAAttribute(s));
        }

        //Perform SELECT Query
        TupleQueryResult t = wa.selectQuery(query);

        //Create List of Bindings associated with the variables (?item ?itemLabel)
        List<String> bn = t.getBindingNames();

        //Add Objects to fca.FormalContext (name of objects are: Q...)
        //This Format is important for later querying
        for (BindingSet b : t){
            String kl = b.getValue(bn.get(0)).toString();
            c.addObject(new FCAObject(kl.substring(kl.lastIndexOf("/")+1)));
        }

        //Check for each Object, if it has an Attribute of the fca.FormalContext
        for(FCAObject o : c.getContextObjects()){
            //Print the Object Name (Q...)
            System.out.println(o.getObjectName());
            System.out.println("Checking if Object has the specified properties: ");
            //For each Attribute specified in the context check if an object has it and
            //if it has an Attribute add it to the Object Attribute List.
            //This is done trough ASK Queries, which are generated
            for(FCAAttribute a : c.getContextAttributes()){
                //Generate ASK Query based on Object Name(Q...) and the Property (P...)
                if(wa.booleanQuery(builder.generateAskQuery(o.getObjectName(),a.getName()))){
                    o.addAttribute(a);
                    a.addObject(o);
                }
            }
        }

        //REDUNDANT PRINTING DUE TO TESTING PURPOSES

        //Print each Object with the corresponding attributes it has
        for(FCAObject o : c.getContextObjects()){
            System.out.println("Object "+o.getObjectName()+" has the following Attributes: ");
            for(FCAAttribute a : o.getAttributes()){
                System.out.println(a.getName());
            }
        }
        System.out.println("----------------------------------------------------");
        //Print each Attribute with the corresponding objects it has
        for (FCAAttribute a : c.getContextAttributes()){
            System.out.println("Attribute "+a.getName()+" has the following Objects: ");
            for(FCAObject o : a.getObjects()){
                System.out.println(o.getObjectName());
            }
        }

        //Display Crosstable on Console and Write output to File
        OutputWriter<FormalContext> o = new FCAOutputWriter();
        o.printToConsole(c);
        o.writeToFile(c,"context_output.txt");
    }
}
