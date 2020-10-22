import org.eclipse.rdf4j.query.*;
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


        WikidataExtraction wa = new WikidataExtraction();
        wa.establishConnection();

        //P509,P22,P25,P3373,P26
        FCAAttribute a = new FCAAttribute();
        String s = "SELECT DISTINCT ?item ?itemLabel\n" +
                "WHERE\n" +
                "{\n" +
                "  ?item wdt:P509 ?o.\n" +
                "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],en\". }\n" +
                "} LIMIT 10";
        TupleQueryResult t = wa.selectQuery(s);
        List<String> bn = t.getBindingNames();
        for (BindingSet b : t){
            String[] spl = b.getValue(bn.get(1)).toString().split("\"");
            FCAObject o = new FCAObject();
            o.setObjectName(spl[1]);
            a.addObject(o);
        }
        //The first 10 objects with "attribute" P509 ...
        System.out.println("ATTRIBUTE: P509");
        for(FCAObject ob : a.getObjects()){
            System.out.println(ob.getObjectName());
        }



    }
}
