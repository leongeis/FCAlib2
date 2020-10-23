import org.eclipse.rdf4j.query.*;
import wikidata.PropertySet;
import wikidata.WikidataExtraction;
import wikidata.WikidataQueryBuilder;
import wikidata.exceptions.NoVariablesException;
import wikidata.exceptions.TooManyPropertiesException;

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
        
        WikidataExtraction wa = new WikidataExtraction();
        wa.establishConnection();

        WikidataQueryBuilder builder = new WikidataQueryBuilder();

        List<String> p = new ArrayList<>();
        p.add("P509");
        p.add("P22");
        p.add("P25");
        p.add("P3373");
        p.add("P26");

        PropertySet prop = new PropertySet(p);
        builder.addVariable("item");
        builder.addVariable("itemLabel");
        String query= null;
        try {
            query = builder.generateSelectQuery(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FCAAttribute a = new FCAAttribute();
        TupleQueryResult t = wa.selectQuery(query);
        List<String> bn = t.getBindingNames();
        for (BindingSet b : t){
            String[] spl = b.getValue(bn.get(1)).toString().split("\"");
            FCAObject o = new FCAObject();
            o.setObjectName(spl[1]);
            a.addObject(o);
        }
        //The first 10 objects with "attribute" P509 ...
        System.out.println("ATTRIBUTE: P509,P22,P25,P3373,P26");
        for(FCAObject ob : a.getObjects()){
            System.out.println(ob.getObjectName());
        }



    }
}
