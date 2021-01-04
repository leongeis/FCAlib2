import api.fca.Attribute;
import api.fca.Context;
import api.fca.ObjectAPI;
import api.utils.ContextHelper;
import api.utils.OutputPrinter;
import api.utils.SPARQLQueryGenerator;
import lib.fca.FCAAttribute;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;
import lib.utils.KnowledgeGraphAccess;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import java.util.List;

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) {

        //Property class URI
        String uri = "http://www.wikidata.org/entity/Q57633168";

        //Create context for wikidata by using the property class for animals/zoology
        Context<String, String> wikidataAnimalContext = ContextHelper.createContextFromWikidata(new FCAFormalContext<String, String>(){},uri,10);

        OutputPrinter.printCrosstableToConsole(wikidataAnimalContext);

        //Get all equivalent entities from DBPedia
        Context<String,String> dbpediaAnimalContext = new FCAFormalContext<String, String>() {};

        KnowledgeGraphAccess knowledgeGraphAccess = new KnowledgeGraphAccess("https://dbpedia.org/sparql");


        knowledgeGraphAccess.establishConnection();
        for(ObjectAPI<String,String> object : wikidataAnimalContext.getContextObjects()){
            FCAObject<String,String> newObject = new FCAObject<>();
            TupleQueryResult result = knowledgeGraphAccess.selectQuery(SPARQLQueryGenerator.generateSelectSameAsQuery(object.getObjectID()));

            List<String> bindingNames = result.getBindingNames();
            for(BindingSet bindings : result){
                newObject.setObjectID(bindings.getValue(bindingNames.get(0)).stringValue());
            }
            dbpediaAnimalContext.addObject(newObject);
        }

        //Iterate over each Attribute of the Wikidata Context and get the equivalent dbpedia property
        for(Attribute<String,String> attribute : wikidataAnimalContext.getContextAttributes()){
            FCAAttribute<String,String> newAttribute = new FCAAttribute<>();
            TupleQueryResult result = knowledgeGraphAccess.selectQuery(SPARQLQueryGenerator.generateSelectEquivalentPropertyQuery(newAttribute.getAttributeID()));
            List<String> bindingNames = result.getBindingNames();
            for(BindingSet bindings : result){
                //ERROR
                newAttribute.setAttributeID(bindings.getValue(bindingNames.get(0)).stringValue());
            }
            dbpediaAnimalContext.addAttribute(newAttribute);

        }

        OutputPrinter.printCrosstableToConsole(dbpediaAnimalContext);








/*

        //*********EXPERIMENTAL*******************************************************

        //Look in utils.output to see the contexts of the created mappings

        //******************************************Wikidata
        SPARQLEndpointAccess wikidataAccess = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");
        wikidataAccess.establishConnection();

        String property = "http://www.wikidata.org/entity/P";

        //Create a List for all Properties of wikidata
        List<String> properties = new ArrayList<>();

        //Use own SPARQL Query to get all properties from wikidata
        String query = "SELECT ?property WHERE {\n" +
                "    ?property a wikibase:Property .\n" +
                " }\n" +
                "GROUP BY ?property";

        //Perform query and save result
        TupleQueryResult propResult = wikidataAccess.selectQuery(query);

        //Binding names
        List<String> bindingN = propResult.getBindingNames();



        //Iterate over each result and add property to the list
        for(BindingSet bindings : propResult){
            properties.add(bindings.getValue(bindingN.get(0)).stringValue());
            System.out.println(bindings.getValue(bindingN.get(0)).stringValue());
        }

        List<Pair<String,List<String>>> wikidataPairs = new ArrayList<>();
        //List<List<String>> predicates = new ArrayList<>();
        long a1 = Performance.setTimeStamp();
        int i = 0;
        while(i<properties.size()){
            //Query equivalent properties
            query=SPARQLQueryGenerator.generateSelectWikidataEquivalentPropertyQuery(properties.get(i));
            TupleQueryResult result = wikidataAccess.selectQuery(query);
            //just property
            List<String> bindingNames = result.getBindingNames();

            List<String> propSet = new ArrayList<>();
            for(BindingSet bset : result){
                propSet.add(bset.getValue(bindingNames.get(0)).toString());
            }
            wikidataPairs.add(new Pair<>(properties.get(i), propSet));
                */
/*List<String> eqProps = new ArrayList<>();
                eqProps.add(property + i);
                eqProps.addAll(propSet);*//*

                //predicates.add(eqProps);
            i++;

            result.close();
        }
        wikidataAccess.closeConnection();
        long a2 = Performance.setTimeStamp();

        System.out.println("*************************************");
        System.out.println(Performance.convertToFormat(Performance.getExecutionTime(a1,a2)));
        //PRINT EQUAL PROPERTIES

        */
/*for(Pair<String,List<String>> p : wikidataPairs){
            if(!p.getRight().isEmpty()) System.out.println(p.getLeft()+" : "+p.getRight());
        }*//*


        //SAVE MAPPING AS CONTEXT
        Context<String,String> wikidataMappingContext = new FCAFormalContext<String, String>(){};
        for(Pair<String,List<String>> p : wikidataPairs){
            FCAObject<String,String> mapping = new FCAObject<>(p.getLeft());
            for(String eqProp : p.getRight()){
                //Only add the uri excluding the last part as Attribute
                mapping.addAttribute(eqProp.substring(0,eqProp.lastIndexOf("/"))+"/");
            }
            wikidataMappingContext.addObject(mapping);
        }
        OutputPrinter.writeCrosstableToFile(wikidataMappingContext,"wikidataMapping2.txt");

        */
/*OutputPrinter.writeStemBaseToFile(wikidataMappingContext,"wikidataMappingStemBase.txt");
        for(Implication<String,String> im : Computation.computeStemBase(wikidataMappingContext)){
            System.out.println(Computation.computeImplicationSupport(im,wikidataMappingContext));
        }*//*



        for(Attribute<String,String> attr : wikidataMappingContext.getContextAttributes()){
            //Compute how many objects have this attribute in percent
            BigDecimal objects = new BigDecimal(Computation.computePrimeOfAttributes(Collections.singletonList(attr),wikidataMappingContext).size());
            BigDecimal allobjects = new BigDecimal(wikidataMappingContext.getContextObjects().size());
            BigDecimal percent = objects.divide(allobjects,5, RoundingMode.HALF_UP);
            System.out.println(attr.getAttributeID()+" "+percent);
        }







        //******************************************DBPedia
        */
/*wikidataAccess.setSparqlEndpoint("https://dbpedia.org/sparql");
        wikidataAccess.establishConnection();
        Context<String,String> dbpediaMappingContext = new FCAFormalContext<String, String>() {};
        for(List<String> eqProperties : predicates){
            String query = SPARQLQueryGenerator.generateSelectPropertyCheckQuery(eqProperties);
            //System.out.println(SPARQLQueryGenerator.generateSelectPropertyCheckQuery(eqProperties));
            TupleQueryResult result = wikidataAccess.selectQuery(query);
            List<String> bindingNames = result.getBindingNames();
            String ID;
            for(BindingSet bindings : result){
                ID = bindings.getValue(bindingNames.get(0)).toString();
                String attribute = bindings.getValue(bindingNames.get(1)).toString();
                String prefix = attribute.substring(0,attribute.lastIndexOf("/"))+"/";
                if(dbpediaMappingContext.containsObject(ID)){
                    dbpediaMappingContext.getObject(ID).addAttribute(prefix);
                }else {
                    FCAObject<String,String> newObject = new FCAObject<>(ID);
                    newObject.addAttribute(prefix);
                    dbpediaMappingContext.addObject(newObject);
                }

            }


        }
        OutputPrinter.writeCrosstableToFile(dbpediaMappingContext,"dbpediaMapping.txt");
        OutputPrinter.writeStemBaseToFile(wikidataMappingContext,"dbpediaMappingStemBase.txt");
        for(Implication<String,String> im : Computation.computeStemBase(dbpediaMappingContext)){
            System.out.println(im.toString()+" "+Computation.computeImplicationSupport(im,dbpediaMappingContext));
        }
*//*

        //TODO
        //***************************************YAGO


*/

    }
}
