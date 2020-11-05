import fca.FCAFormalContext;
import utils.FCAOutputWriter;
import utils.ContextHelper;


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

        FCAFormalContext<String,String> context = ContextHelper.createContextFromWikidata(true,null,null);

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

        //TODO Implement Expert and use algorithms

    }
}
