package wikidata;

import wikidata.exceptions.NoVariablesException;
import wikidata.exceptions.TooManyPropertiesException;

import java.util.ArrayList;
import java.util.List;

/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */




//For more information on SPARQL Queries, consider: https://www.w3.org/2009/Talks/0615-qbe/

//TODO: Implementation of Class WikidataQueryBuilder (21.10.20)
/**
 * Class used for creating simple SPARQL Queries, which are then
 * used at the SPARQL Endpoint of Wikidata. This enables a simple
 * SPARQL Query to be built, without exact knowledge of the syntax
 * of a SPARQL Query.
 * @author Leon Geis
 */
public class WikidataQueryBuilder {

    /**
     * The Limit specifies the amount of queried individuals.
     */
    private static final int LIMIT=100;

    /**
     * Specifying the amount of properties.
     */
    private static final int PROPERTY_COUNT=5;

    /**
     * Language of the Item Labels.
     * Standard: English
     */
    private String language;


    /**
     * A List of prefixes for the query.
     * (PREFIX ex:<http://example.org/> ...)
     */
    private List<String> prefixes;

    /**
     * A List of variables for the query.
     * (?s ?p ?o)
     */
    private List<String> variables;

    /**
     * The Constructor of the Class.
     */
    public WikidataQueryBuilder(){
        prefixes = new ArrayList<>();
        variables = new ArrayList<>();
        this.language="en";
    }

    /**
     * Get the permitted amount of Properties.
     * @return Number of Properties.
     */
    public int getPropertyCount(){
        return PROPERTY_COUNT;
    }


    /**
     * Adding a Variable to the list.
     * @param var String of the variable.
     */
    public void addVariable(String var){
        this.variables.add(var);
    }


    //TODO REWORK generateSelectQuery!!!
    /**
     * Generates a SELECT Query based on the given properties and
     * returns a String representation of it. The amount of possible
     * properties is limited.
     * @param properties The set of the specified properties.
     * @return A String representation of the SELECT Query.
     */
    public String generateSelectQuery(PropertySet properties) throws TooManyPropertiesException,NoVariablesException {

        //Limiting the amount of properties to 5
        if(properties.getSize()>PROPERTY_COUNT){
            throw new TooManyPropertiesException("Too many Properties are specified. Currently permitted: "+PROPERTY_COUNT);
        }
        //Check if a variable was added.
        if(this.variables.size()<1){
            throw new NoVariablesException("No Variables are specified.");
        }


        //Initializing result query String
        String resultQuery="SELECT ";

        //Adding variables to be queried
        for(String var : this.variables){
            resultQuery=resultQuery.concat("?"+var+" ");
        }

        //Adding WHERE Condition
        resultQuery=resultQuery.concat("WHERE {");

        //For each property add Query String to resultQuery
        int i=0;
        for(String s : properties.getProperties()){
            //Adding String of format: e.g. : {?item wdt:509 ?o.}
            resultQuery=resultQuery.concat("{?"+this.variables.get(0)+" wdt:"+s+" ?o.} ");
            if(i<properties.getSize()-1) resultQuery=resultQuery.concat("UNION ");
            i++;
        }

        //Adding Wikidata Service Label Translation with specified language
        resultQuery=resultQuery.concat(" SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],"+this.language+"\". }}");

        //Specifying the Limit of individuals, which are returned by the query
        resultQuery=resultQuery.concat("LIMIT "+LIMIT);

        //Return the generated SELECT Query String
        return resultQuery;
    }



}
