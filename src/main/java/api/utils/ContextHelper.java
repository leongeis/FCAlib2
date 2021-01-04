package api.utils;

import api.fca.Attribute;
import api.fca.Context;
import api.fca.ObjectAPI;
import lib.fca.FCAAttribute;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;
import lib.utils.KnowledgeGraphAccess;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//TODO Rework JavaDoc

/**
 * Interface, which can be used to easily create a Context
 * from a Knowledge Graph. When using a method from
 * this interface, only subjects of the queries are
 * stored as objects in the Context.
 * @author Leon Geis
 */
public interface ContextHelper {

    /**
     * Creates (or "fills") a Context object with data from Wikidata.
     * The first parameter is a Context Object, which can be of any class
     * implementing the Context interface. The second parameter is the URI
     * of a property class. The third one specifies the number of objects
     * that are stored in the Context object.
     * @param context Context Object.
     * @param propertyClass String of the URI of the property Class.
     * @param number Number of Objects.
     * @return Context Object with subjects of the query as objects of the Context
     * and subproperties of the property Class as Attributes.
     */
    static Context<String,String> createContextFromWikidata(Context<String,String> context, String propertyClass, int number){
        //Clear the List of Attributes and the List of Objects of the Context
        context.getContextObjects().clear();
        context.getContextAttributes().clear();
        //Create new Object of type KnowledgeGraphAccess and
        //set the URL of the SPARQL Endpoint
        KnowledgeGraphAccess wa = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");
        //Establish a connection to the SPARQL Endpoint of Wikidata
        wa.establishConnection();

        //Query Wikidata to receive a List of Properties of the given property Class

        //Perform instance of Query to receive all associated properties
        TupleQueryResult classResult = wa.selectQuery(SPARQLQueryGenerator.generateSelectWikidataInstanceOfQuery(propertyClass));
        System.out.println("asdasdasdasdasdasd");
        System.out.println(SPARQLQueryGenerator.generateSelectWikidataInstanceOfQuery(propertyClass));
        //Create List of Bindings associated with the variables (?s)
        List<String> bindingNames = classResult.getBindingNames();
        //Create corresponding prefix
        String prefix = "http://www.wikidata.org/prop/direct/";

        //Iterate over the result and add the properties to the context
        for(BindingSet bindingSet : classResult){
            String uri = bindingSet.getValue(bindingNames.get(0)).stringValue();
            context.createAttribute(prefix+ uri.substring(uri.lastIndexOf('/')+1),FCAAttribute.class);
        }
        System.out.println(context.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toList()));
        //Close Result object
        classResult.close();
        //Now Perform SELECT Query and get the Objects according to the Attributes of the Context
        //Note the use of the propertyURI List, this is due to the result of a query, e.g., for the property
        //P25 is: http://www.wikidata.org/entity/P25, but to use them as a predicate we need
        //http://www.wikidata.org/prop/direct/P25.
        TupleQueryResult result = wa.selectQuery(SPARQLQueryGenerator.generateSelectUnionQuery(context.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toList()), number));

        //Create List of Bindings associated with the variables
        bindingNames = result.getBindingNames();

        //Add Objects to fca.FCAFormalContext (name of objects are: Q...)
        //This Format is important for later querying
        //Note: Only subjects of the query are saved as objects of the context.
        //Hence the line b.getValue(bn.get(0)).toString(); also saving objects is possible
        //through exchanging the 0 with 2. Also saving both is possible!
        for (BindingSet bindingSet : result){
            String uri = bindingSet.getValue(bindingNames.get(0)).stringValue();
            //Get Identifier of the Subject
            //String identifier = kl.substring(kl.lastIndexOf("/")+1);
            //Checking if Object is already in Object List of the context
            //If not add it to the context.
            if(!context.containsObject(uri)){
                context.createObject(uri, FCAObject.class);
            }
        }
        result.close();
        //Check for each Object, if it has an Attribute of the Context
        for(ObjectAPI<String,String> o : context.getContextObjects()){
            result = wa.selectQuery(SPARQLQueryGenerator.generateSelectPropertyCheckQuery(o.getObjectID(),context.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toList())));
            //Get the binding Names
            bindingNames = result.getBindingNames();
            //Create List which contains each binding
            List<String> identifiers = new ArrayList<>();
            //Iterate over results and add Attribute if it is contained in the results
            for(BindingSet bindings : result){
                //Get uri
                String uri = bindings.getValue(bindingNames.get(0)).stringValue();
                //Get value
                //String value = uri.substring(uri.lastIndexOf("/")+1);
                if(!identifiers.contains(uri)){
                    identifiers.add(uri);
                    //Add corresponding Incidence
                    o.addAttribute(uri);
                    context.getAttribute(uri).addObject(o.getObjectID());
                }
            }
            result.close();
        }
        //Close connection
        wa.getConnection().close();
        //Return Context Object
        return context;
    }

    //TODO
    static FCAFormalContext<String,String> createContextFromDBPedia(){
        return null;
    }
    //TODO
    static FCAFormalContext<String,String> createContextFromYAGO(){
        return null;
    }

}
