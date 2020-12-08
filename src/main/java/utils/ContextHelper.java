package utils;

import api.Attribute;
import api.ObjectAPI;
import fca.FCAFormalContext;
import fca.FCAObject;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import utils.exceptions.NoPropertiesDefinedException;
import wikidata.SPARQLQueryBuilder;
import wikidata.WikidataExtraction;

import java.util.List;
/**
 * Used for creating a FCAFormalContext Object based on data from Wikidata.
 * @author Leon Geis
 */
public class ContextHelper {

    //TODO REWORK GENERICS

    /**
     * Method used for creating a new FormalContext. This Context is
     * then "filled" with data from wikidata. If the first parameter is true, then
     * a query as the second parameter is obsolete. Thus to use a custom query, set the first
     * parameter to false and provide a String of the query as the second parameter.
     * If the third parameter is
     * null, the user can choose from a list of files in the package properties.
     * @param build If a Builder Object should be used. If not a query has to be provided.
     * @param qry String of the query, which is used at the SPARQL Endpoint of Wikidata.
     * @param file String of the file name the properties should be extracted from.
     * @return A FCAFormalContext Object, which is set according to the specified data from Wikidata.
     */
    //Note: Currently only Plain Incidence is implemented! (4.11.20)
    public static FCAFormalContext<String,String> createContextFromWikidata(boolean build, String qry, String file){
        //Create new FormalContext
        FCAFormalContext<String,String> c = new FCAFormalContext<String,String>(){};
        //Create new Object of type WikidataExtraction
        WikidataExtraction wa = new WikidataExtraction();
        //Establish a connection to the SPARQL Endpoint of Wikidata
        wa.establishConnection();
        //If build is set to true, use SPARQLQueryBuilder object
        //Note: Limit cannot be set using this method. Has to be done beforehand.
        SPARQLQueryBuilder builder=null;
        if(build){
            builder = new SPARQLQueryBuilder();
        }
        //Create a PropertyIO object and read properties from file
        PropertyIO prop=null;
        try {
            prop = new PropertyIO();
        } catch (NoPropertiesDefinedException e) {
            e.printStackTrace();
        }
        //Read from specified file (null = user can choose the used file)
        //file has to be in package properties!
        //If a file has been specified use the given String.
        assert prop != null;
        prop.readFromFile(file);

        //If query is null a query will be generated, otherwise the given query will be used.
        //(Optional) Generate a SELECT Query based on the properties
        String query= null;
        if(qry==null){
            try {
                assert builder != null;
                query = builder.generateSelectQuery(prop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            query=qry;
        }
        //Initialize fca.FCAAttribute List
        for(String s : prop.getProperties()){
            c.createFCAAttribute(s);
        }
        //Perform SELECT Query
        TupleQueryResult t = wa.selectQuery(query);

        //Create List of Bindings associated with the variables (?item ?itemLabel)
        List<String> bn = t.getBindingNames();

        //Add Objects to fca.FCAFormalContext (name of objects are: Q...)
        //This Format is important for later querying
        //Note: Only subjects of the query are saved as objects of the context.
        //Hence the line b.getValue(bn.get(0)).toString(); also saving objects is possible
        //through exchanging the 0 with 2. Also saving both is possible!
        for (BindingSet b : t){
            String kl = b.getValue(bn.get(0)).toString();
            //Get Identifier of the Subject
            String identifier = kl.substring(kl.lastIndexOf("/")+1);
            //Checking if Object is already in Object List of the context
            //If not add it to the context.
            if(!c.containsObject(identifier)){
                c.createObject(identifier, FCAObject.class);
            }
        }
        //Check for each Object, if it has an Attribute of the fca.FCAFormalContext
        for(ObjectAPI<String,String> o : c.getContextObjects()){
            //For each Attribute specified in the context check if an object has it and
            //if it has an Attribute add it to the Object Attribute List.
            //This is done trough ASK Queries, which are generated respectively
            for(Attribute<String,String> a : c.getContextAttributes()){
                //Generate ASK Query based on Object Name(Q...) and the Property (P...)
                if(wa.booleanQuery(builder.generateAskQuery(o.getObjectID(),a.getAttributeID()))){
                    o.addAttribute(a.getAttributeID());
                    a.addObject(o.getObjectID());
                }
            }
        }
        //Return FCAFormalContext Object
        return c;
    }

    //TODO
    public static FCAFormalContext<String,String> createContextFromDBPedia(){
        return null;
    }
    //TODO
    public static FCAFormalContext<String,String> createContextFromYAGO(){
        return null;
    }

}
