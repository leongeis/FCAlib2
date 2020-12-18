import api.fca.Computation;
import api.fca.Context;
import api.fca.Implication;
import api.utils.OutputPrinter;
import api.utils.SPARQLQueryGenerator;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;
import lib.utils.KnowledgeGraphAccess;
import lib.utils.Pair;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import java.util.ArrayList;
import java.util.List;

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) {

        //*********EXPERIMENTAL*******************************************************

        //Look in utils.output to see the contexts of the created mappings

        //******************************************Wikidata
        KnowledgeGraphAccess we = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");
        we.establishConnection();
        String property = "http://www.wikidata.org/entity/P";
        int i=1;
        List<Pair<String,List<String>>> wikidataPairs = new ArrayList<>();
        List<List<String>> predicates = new ArrayList<>();
        while(i<100){
            String query=SPARQLQueryGenerator.generateSelectWikidataEquivalentPropertyQuery(property+i);
            TupleQueryResult result = we.selectQuery(query);
            //just o
            List<String> bindingNames = result.getBindingNames();

            List<String> propse = new ArrayList<>();
            for(BindingSet bset : result){
                propse.add(bset.getValue(bindingNames.get(0)).toString());
            }
            wikidataPairs.add(new Pair<>(property+i,propse));
            List<String> eqProps = new ArrayList<>();
            eqProps.add(property+i);
            eqProps.addAll(propse);
            predicates.add(eqProps);
            result.close();
            i++;
        }
        we.closeConnection();

        //PRINT EQUAL PROPERTIES
        /*for(Pair<String,List<String>> p : wikidataPairs){
            if(!p.getRight().isEmpty()) System.out.println(p.getLeft()+" : "+p.getRight());
        }*/
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
        OutputPrinter.writeCrosstableToFile(wikidataMappingContext,"wikidataMapping.txt");
        OutputPrinter.writeStemBaseToFile(wikidataMappingContext,"wikidataMappingStemBase.txt");
        for(Implication<String,String> im : Computation.computeStemBase(wikidataMappingContext)){
            System.out.println(Computation.computeImplicationSupport(im,wikidataMappingContext));
        }

        //******************************************DBPedia
        we.setSparqlEndpoint("https://dbpedia.org/sparql");
        we.establishConnection();
        Context<String,String> dbpediaMappingContext = new FCAFormalContext<String, String>() {};
        for(List<String> eqProperties : predicates){
            String query = SPARQLQueryGenerator.generateSelectPropertyCheckQuery(eqProperties);
            //System.out.println(SPARQLQueryGenerator.generateSelectPropertyCheckQuery(eqProperties));
            TupleQueryResult result = we.selectQuery(query);
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

        //TODO
        //***************************************YAGO


    }
}
