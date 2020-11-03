package wikidata;

import utils.PropertySet;
import utils.exceptions.NoVariablesException;
import utils.exceptions.TooManyPropertiesException;

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

/**
 * Class used for creating simple SPARQL Queries, which are then
 * used at the SPARQL Endpoint of Wikidata. This enables a simple
 * SPARQL Query to be built, without exact knowledge of the syntax
 * of a SPARQL Query.
 * @author Leon Geis
 */
public class SPARQLQueryBuilder {

    /**
     * The Limit specifies the amount of queried individuals.
     */
    private static int LIMIT=10;

    /**
     * Specifying the amount of properties.
     */
    private static int PROPERTY_COUNT=5;

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
    public SPARQLQueryBuilder(){
        prefixes = new ArrayList<>();
        variables = new ArrayList<>();
        this.language="en";
    }

    /**
     * Set the Limit of the queried Individuals.
     * @param limit int
     */
    public void setLimit(int limit){
        LIMIT = limit;
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


    /**
     * Generates a SELECT Query based on the given properties and
     * returns a String representation of it. The amount of possible
     * properties is limited.
     * @param properties The set of the specified properties.
     * @return A String representation of the SELECT Query.
     */
    public String generateSelectQuery(PropertySet properties) throws TooManyPropertiesException {

        //Limiting the amount of properties to 5
        if(properties.getSize()>PROPERTY_COUNT){
            throw new TooManyPropertiesException("Too many Properties are specified. Currently permitted: "+PROPERTY_COUNT);
        }

        //Initializing result query String
        String resultQuery="SELECT ";

        //Check if a variable was added and add them to the query.
        //If not add an asterisk.
        if(this.variables.size()<1){
            resultQuery=resultQuery.concat("* ");
        }else {
            //Adding variables to be queried
            for (String var : this.variables) {
                resultQuery = resultQuery.concat("?" + var + " ");
            }
        }

        //Adding WHERE Condition
        resultQuery=resultQuery.concat("WHERE {");

        //For each property add Query String to resultQuery
        int i=0;
        for(String p : properties.getProperties()){
            //Adding String of format: e.g. : {?item wdt:509 ?o.}
            resultQuery=resultQuery.concat("{?s wdt:"+p+" ?o.} ");
            if(i<properties.getSize()-1) resultQuery=resultQuery.concat("UNION ");
            i++;
        }

        //Adding Wikidata Service Label Translation with specified language (HEAVILY DECREASES PERFORMANCE)
        //resultQuery=resultQuery.concat(" SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],"+this.language+"\". ");

        // TODO INCREASE QUERY PERFORMANCE
        //Specifying the Limit of individuals, which are returned by the query
        if(LIMIT>0) resultQuery=resultQuery.concat("} LIMIT "+LIMIT);
        //Return the generated SELECT Query String
        return resultQuery;
    }

    /**
     * Generates a ASK Query based on the given item name (Q...)
     * and the property (P...). Used for Property Checking.
     * Note: When the statement is deprecated, false is returned!
     * @param item String of form (Q...)
     * @param property String of form (P...)
     * @return ASK Query String
     */
    public String generateAskQuery(String item, String property){
        return "ASK WHERE {wd:"+item+" wdt:"+property+" ?o.}";
    }


}