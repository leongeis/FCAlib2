import api.OutputWriter;
import fca.FCAAttribute;
import fca.FCAFormalContext;
import fca.FCAObject;
import utils.FCAOutputWriter;
import wikidata.ContextHelper;


/*For further information on RDF4J connections:
*https://rdf4j.org/documentation/programming/repository/#using-a-repository-repositoryconnections
*(Accessed: 20.10.20 14:34 (MESZ))
 */

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args){

        //Create a FCAFormalContext Object and initialize it with Data from Wikidata
        //first parameter: a SPARQLQueryBuilder Object is used for generating a query
        //making the second parameter obsolete. Hence null.
        //third parameter: specifying a file name, the properties are read from. When null is provided,
        //the user can choose from a list of files.

        //This approach always returns an object with types FCAObject and FCAAttribute.
        //Thus one can split this line to enable a more generic approach to create a FCAFormalContext Object.

        FCAFormalContext<FCAObject,FCAAttribute> context = ContextHelper.createContextFromWikidata(true,null,null);

        //Here a Context is created and the type of the Objects and Attributes are Strings
        FCAFormalContext<String,String> exampleContext = new FCAFormalContext<>();

        //Display Crosstable on Console and Write output to File
        OutputWriter<FCAFormalContext<FCAObject,FCAAttribute>> o = new FCAOutputWriter();
        o.printToConsole(context);
        o.writeToFile(context,"context_output.txt");



    }
}
