import api.OutputWriter;
import fca.FCAAttribute;
import fca.FCAObject;
import fca.FormalContext;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import utils.FCAOutputWriter;
import utils.PropertySet;
import utils.exceptions.NoPropertiesDefinedException;
import wikidata.SPARQLQueryBuilder;
import wikidata.WikidataExtraction;

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

    public static void main(String[] args) throws NoPropertiesDefinedException {

        //Create new Object of type WikidataExtraction
        WikidataExtraction wa = new WikidataExtraction();
        //Establish a connection to the SPARQL Endpoint of Wikidata
        wa.establishConnection();

        //(Optional) Create a SPARQL query builder object
        SPARQLQueryBuilder builder = new SPARQLQueryBuilder();

        //(Optional) Set the Limit of the received Entities to 25 (Standard Limit is set to 10)
        //builder.setLimit(25);

        //(Optional) Set the Limit of received Entities to 0 to retrieve the whole list
        //builder.setLimit(0);

        //Create a PropertySet object and read properties from file
        PropertySet prop = new PropertySet();

        //Read from specified file (null = user can choose the used file)
        //file has to be in package properties!
        prop.readFromFile(null);

        //(Optional) add Variables to the SPARQLQueryBuilder object
        //builder.addVariable("item");
        //builder.addVariable("itemLabel");

        //(Optional) Generate a SELECT Query based on the properties
        String query= null;
        try {
            query = builder.generateSelectQuery(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Print generated query for testing purposes
        System.out.println(query);
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
        //Note: Only subjects of the query are saved as objects of the context.
        //Hence the line b.getValue(bn.get(0)).toString(); also saving objects is possible
        //through exchanging the 0 with 2. Also saving both is possible!
        for (BindingSet b : t){
            String kl = b.getValue(bn.get(0)).toString();
            c.addObject(new FCAObject(kl.substring(kl.lastIndexOf("/")+1)));
        }

        //Check for each Object, if it has an Attribute of the fca.FormalContext
        for(FCAObject o : c.getContextObjects()){
            //For each Attribute specified in the context check if an object has it and
            //if it has an Attribute add it to the Object Attribute List.
            //This is done trough ASK Queries, which are generated respectively
            for(FCAAttribute a : c.getContextAttributes()){
                //Generate ASK Query based on Object Name(Q...) and the Property (P...)
                if(wa.booleanQuery(builder.generateAskQuery(o.getObjectName(),a.getName()))){
                    o.addAttribute(a);
                    a.addObject(o);
                }
            }
        }

        //Display Crosstable on Console and Write output to File
        OutputWriter<FormalContext> o = new FCAOutputWriter();
        o.printToConsole(c);
        o.writeToFile(c,"context_output.txt");

    }
}
