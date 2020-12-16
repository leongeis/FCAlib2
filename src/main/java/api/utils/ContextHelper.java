package api.utils;

import api.fca.Attribute;
import api.fca.ObjectAPI;
import lib.fca.FCAAttribute;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;
import lib.utils.PropertyIO;
import lib.utils.exceptions.NoPropertiesDefinedException;
import lib.wikidata.WikidataExtraction;
import lib.wikidata.WikidataQueryBuilder;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used for creating a FCAFormalContext Object based on data from Wikidata.
 * @author Leon Geis
 */
public interface ContextHelper {

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
    static FCAFormalContext<String,String> createContextFromWikidata(boolean build, String qry, String file){
        //Create new FormalContext
        FCAFormalContext<String,String> c = new FCAFormalContext<String,String>(){};
        //Create new Object of type WikidataExtraction
        WikidataExtraction wa = new WikidataExtraction();
        //Establish a connection to the SPARQL Endpoint of Wikidata
        wa.establishConnection();
        //If build is set to true, use WikidataQueryBuilder object
        //Note: Limit cannot be set using this method. Has to be done beforehand.
        WikidataQueryBuilder builder=null;
        if(build){
            builder = new WikidataQueryBuilder();
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
                //If only one property is specified the class (instance of) properties will be
                //queries and a new PropertyIO object is created
                if(prop.getSize()==1){
                    Iterator<String> iterator = prop.getProperties().iterator();
                    String propertyClass = iterator.next();
                    //Perform instance of Query to receive all associated properties
                    TupleQueryResult classResult = wa.selectQuery(builder.generateWikidataInstanceOfQuery(propertyClass));
                    //Create List of Bindings associated with the variables (?item)
                    List<String> bindingNames = classResult.getBindingNames();
                    //Get all Properties and save them in the PropertyIO object
                    prop.getProperties().remove(propertyClass);
                    for(BindingSet set : classResult){
                        //Add each property to the property Object
                        prop.addProperty(set.getValue(bindingNames.get(0)).toString().substring(set.getValue(bindingNames.get(0)).toString().lastIndexOf("/")+1));
                    }
                }
                query = builder.generateSelectQuery(prop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            query=qry;
        }
        //Initialize fca.FCAAttribute List
        for(String s : prop.getProperties()){
            c.createAttribute(s, FCAAttribute.class);
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
        //Create Query Builder Object
        builder = new WikidataQueryBuilder();
        System.out.println(builder.generateSelectBindQuery("Q90227",c.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toList())));
        //Check for each Object, if it has an Attribute of the fca.FCAFormalContext
        for(ObjectAPI<String,String> o : c.getContextObjects()){
            TupleQueryResult result = wa.selectQuery(builder.generateSelectBindQuery(o.getObjectID(),c.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toList())));
            //Get the binding Names
            List<String> bindingNames = result.getBindingNames();
            //Create List which contains each binding
            List<String> identifiers = new ArrayList<>();
            //Iterate over results and add Attribute if it is contained in the results
            for(BindingSet bindings : result){
                //Get uri
                String uri = bindings.getValue(bindingNames.get(0)).toString();
                //Get value
                String value = uri.substring(uri.lastIndexOf("/")+1);
                if(!identifiers.contains(value)){
                    identifiers.add(value);
                    //Add corresponding Incidence
                    o.addAttribute(value);
                    c.getAttribute(value).addObject(o.getObjectID());
                }
            }
        }
        //Return FCAFormalContext Object
        return c;
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
