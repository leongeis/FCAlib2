import api.fca.Computation;
import api.fca.Context;
import api.fca.Implication;
import api.utils.Performance;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) throws IOException {

        FCAObject<String,String> ob1 = new FCAObject<>("1");
        ob1.addAttribute("b");
        ob1.addAttribute("d");
        FCAObject<String,String> ob2 = new FCAObject<>("2");
        ob2.addAttribute("b");
        ob2.addAttribute("e");
        FCAObject<String,String> ob3 = new FCAObject<>("3");
        ob3.addAttribute("c");

        FCAObject<String,String> ob4 = new FCAObject<>("4");
        ob4.addAttribute("a");
        ob4.addAttribute("b");
        ob4.addAttribute("c");
        FCAObject<String,String> ob5 = new FCAObject<>("5");

        ob5.addAttribute("d");
        FCAObject<String,String> ob6 = new FCAObject<>("6");
        ob6.addAttribute("b");
        ob6.addAttribute("c");
        FCAObject<String,String> ob7 = new FCAObject<>("7");
        ob7.addAttribute("e");

        Context<String,String> context = new FCAFormalContext<String, String>() {};
        context.addObject(ob1);
        context.addObject(ob2);
        context.addObject(ob3);
        context.addObject(ob4);
        context.addObject(ob5);
        context.addObject(ob6);
        context.addObject(ob7);

        List<Implication<String,String>> impls = Computation.computeStemBase(context);
        for(Implication<String,String> impl : impls){
            System.out.println(impl.toString());
        }

        //****************WIKIDATA*********************
        //Property class
        //String property = "http://www.wikidata.org/entity/Q19829914";
        //Context<String,String> wikidataHumanContext = ContextHelper.createContextFromWikidata(new FCAFormalContext<String, String>() {},property,8192);
        List<String> properties = new ArrayList<>();
        char a = 'a';
        for(int i=0; i<25;++i){
            properties.add(Character.toString(a+i));
        }
        System.out.println(properties);
        //properties.add("d");
        //Add Objects with random incidence
        int amountObjects = 5;
        while(amountObjects <= 100) {
            Context<String,String> wikidataHumanContext = new FCAFormalContext<String, String>() {};
            for (int i = 0; i < amountObjects; ++i) {
                FCAObject<String, String> object = new FCAObject<>(String.valueOf(i));
                for (String prop : properties) {
                    int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                    if (randomNum >= 50) {
                        object.addAttribute(prop);
                    }
                }
                wikidataHumanContext.addObject(object);
            }

            long b1 = Performance.setTimeStamp();
            //Computation.reduceContext(wikidataHumanContext);
            System.out.println("**********************OBJECTS==" + amountObjects);
            //OutputPrinter.printCrosstableToConsole(wikidataHumanContext);
            List<Implication<String, String>> implications = Computation.computeStemBase(wikidataHumanContext);
            /*for (Implication<String, String> implication : implications) {
                double support = Computation.computeImplicationSupport(implication, wikidataHumanContext);
                System.out.println(implication.toString() + " " + support);
            }*/
            long b2 = Performance.setTimeStamp();
            //OutputPrinter.printCrosstableToConsole(wikidataHumanContext);
            System.out.println(Performance.convertToFormat(Performance.getExecutionTime(b1, b2)));
            System.out.println(implications.size());
            amountObjects +=5;
        }
        /*KnowledgeGraphAccess knowledgeGraphAccess = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");

        Context<String,String> wikidataHumanContext = new FCAFormalContext<String, String>() {};

        knowledgeGraphAccess.establishConnection();

        String query = "select * where{\n" +
                "  ?s wdt:P31 wd:Q5.} Limit 12";

        TupleQueryResult result = knowledgeGraphAccess.selectQuery(query);

        List<String> bindingNames = result.getBindingNames();

        for(BindingSet bindingSet : result){

            ObjectAPI<String,String> object = new FCAObject<>(bindingSet.getValue(bindingNames.get(0)).stringValue());
            wikidataHumanContext.addObject(object);

        }
        result.close();

        //Get ALL properties of these humans
        for(ObjectAPI<String,String> objectAPI : wikidataHumanContext.getContextObjects()){
            String objectQuery = new SPARQLQueryBuilder().select().variable("?p").where().variable("<"+objectAPI.getObjectID()+">").
                    variable("?p ").variable("?o .").end().groupBy("?p").build();
            result = knowledgeGraphAccess.selectQuery(objectQuery);
            bindingNames=result.getBindingNames();
            for(BindingSet bindingSet : result){
                if(!wikidataHumanContext.containsAttribute(bindingSet.getValue(bindingNames.get(0)).stringValue())){
                    Attribute<String,String> attribute = new FCAAttribute<>(bindingSet.getValue(bindingNames.get(0)).stringValue());
                    wikidataHumanContext.addAttribute(attribute);
                }
            }
            result.close();
        }

        //OutputPrinter.printCrosstableToConsole(wikidataHumanContext);
        System.out.println(wikidataHumanContext.getContextAttributes().size());

        int start=0, finish=10;
        List<List<Attribute<String,String>>> blocks = new ArrayList<>();
        while(start<1010){
            blocks.add(wikidataHumanContext.getContextAttributes().subList(start,finish));
            start += 10;
            finish += 10;
        }
        System.out.println(blocks.size());


        //Now go through each object and perform bind query for each block and add incidence to context
        for(ObjectAPI<String,String> objectAPI : wikidataHumanContext.getContextObjects()){
            List<String> results = new ArrayList<>();
            for(List<Attribute<String,String>> block : blocks){
                query = SPARQLQueryGenerator.generateSelectPropertyCheckQuery(objectAPI.getObjectID(),block.stream().map(Attribute::getAttributeID).collect(Collectors.toList()));
                result = knowledgeGraphAccess.selectQuery(query);
                bindingNames = result.getBindingNames();
                for(BindingSet bindingSet : result){
                    if(!results.contains(bindingSet.getValue(bindingNames.get(0)).stringValue())){
                        results.add(bindingSet.getValue(bindingNames.get(0)).stringValue());
                        objectAPI.addAttribute(bindingSet.getValue(bindingNames.get(0)).stringValue());
                        wikidataHumanContext.getAttribute(bindingSet.getValue(bindingNames.get(0)).stringValue()).addObject(objectAPI.getObjectID());
                    }
                }
            }
        }
        */
        /*long a1 = Performance.setTimeStamp();
        List<Implication<String,String>> implications = Computation.computeStemBase(wikidataHumanContext);

        //Save RESULTS
        FileWriter fw = new FileWriter("WikidataHumanStemBase.txt");

        //minsupp = 70%
        for(Implication<String,String> implication : implications){
            double support = Computation.computeImplicationSupport(implication,wikidataHumanContext);
            if(support >= 0.7){
                fw.write(implication.toString()+" "+support);
                fw.write("\n");
            }
            //System.out.println(implication.toString()+" "+support);

        }
        fw.close();
        long a2 = Performance.setTimeStamp();

        System.out.println("Computing Stem Base took: "+Performance.convertToFormat(Performance.getExecutionTime(a1,a2)));*/



        //The approach below caused a server time out after ~2 hours
        /*long a1 = Performance.setTimeStamp();
       //Create KnowledgeGraphAccess object
        KnowledgeGraphAccess knowledgeGraphAccess = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");

        Context<String,String> wikidataContext = new FCAFormalContext<String, String>() {};

        knowledgeGraphAccess.establishConnection();

        //Use own SPARQL Query to get all properties from wikidata
        String query = "SELECT ?property WHERE {\n" +
                "    ?property a wikibase:Property .\n" +
                " }\n" +
                "GROUP BY ?property";

        TupleQueryResult result = knowledgeGraphAccess.selectQuery(query);

        List<String> bindingNames = result.getBindingNames();

        String propertyPrefix = "http://www.wikidata.org/prop/direct/";
        //Only take first 2000 properties
        int i=0;
        for (BindingSet bindingSet : result) {
            if(i==8000)break;
            //System.out.println(bindingSet.getValue(bindingNames.get(0)));
            String attr = bindingSet.getValue(bindingNames.get(0)).stringValue();
            FCAAttribute<String, String> attribute = new FCAAttribute<>(propertyPrefix + attr.substring(attr.lastIndexOf("/") + 1));
            wikidataContext.addAttribute(attribute);
            ++i;
        }
        result.close();

        List<String> wikprops = wikidataContext.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toList());
        System.out.println(wikprops.size());
        List<List<String>> propblocks = new ArrayList<>();
        int a = 0, b=50;
        while(a<wikprops.size()){
            propblocks.add(wikprops.subList(a,b));
            a += 50;
            b += 50;
        }
        for(List<String> prop : propblocks) {
            query = SPARQLQueryGenerator.generateSelectUnionQuery(prop, 10000);

            result = knowledgeGraphAccess.selectQuery(query);

            bindingNames = result.getBindingNames();

            for (BindingSet bindingSet : result) {
                //System.out.println(bindingSet.getValue(bindingNames.get(0)).stringValue());
                //Add each object to the context
                FCAObject<String, String> object = new FCAObject<>();
                object.setObjectID(bindingSet.getValue(bindingNames.get(0)).stringValue());
                wikidataContext.addObject(object);
            }
            result.close();
        }

        //Split the Attributes due to time exception
        List<List<Attribute<String,String>>> blocks = new ArrayList<>();
        int start = 0, finish = 50;
        while(start<wikidataContext.getContextAttributes().size()){
            blocks.add(wikidataContext.getContextAttributes().subList(start,finish));
            start += 50;
            finish += 50;
        }


        for(ObjectAPI<String,String> objectAPI : wikidataContext.getContextObjects()){
            for(List<Attribute<String,String>> block : blocks) {
                query = SPARQLQueryGenerator.generateSelectPropertyCheckQuery(objectAPI.getObjectID(), block.stream().map(Attribute::getAttributeID).collect(Collectors.toList()));
                result = knowledgeGraphAccess.selectQuery(query);
                bindingNames = result.getBindingNames();

                //System.out.println(objectAPI.getObjectID());
                for (BindingSet bindingSet : result) {
                    //System.out.println(bindingSet.getValue(bindingNames.get(0)).stringValue());
                    objectAPI.addAttribute(bindingSet.getValue(bindingNames.get(0)).stringValue());
                    wikidataContext.getAttribute(bindingSet.getValue(bindingNames.get(0)).stringValue()).addObject(objectAPI.getObjectID());
                }
            }


        }
        List<Implication<String,String>> implications = Computation.computeStemBase(wikidataContext);

        //Save RESULTS
        FileWriter fw = new FileWriter("WikidataStemBase.txt");

        //minsupp = 70%
        for(Implication<String,String> implication : implications){
            double support = Computation.computeImplicationSupport(implication,wikidataContext);
            if(support >= 0.7){
                fw.write(implication.toString()+" "+support);
                fw.write("\n");
            }
                //System.out.println(implication.toString()+" "+support);

        }
        fw.close();

        long a2 = Performance.setTimeStamp();
        System.out.println("This all took: "+Performance.convertToFormat(Performance.getExecutionTime(a1,a2)));
        */
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
