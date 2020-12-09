package api;

import utils.PropertyIO;
import utils.exceptions.TooManyPropertiesException;

/**
 * Interface describing a SPARQL Query builder for Wikidata.
 * This interface can be used to generate a SPARQL Query,
 * specifically for Wikidata. The resulting Query will
 * always be represented as a String.
 * Note: One can also write own Queries and save them
 * as a String. For more information on how to write own
 * SPARQL Queries, see <a href="https://www.w3.org/TR/rdf-sparql-query/">https://www.w3.org/TR/rdf-sparql-query/</a>
 * @author Leon Geis
 */
public interface WikidataSPARQLQueryBuilder {

    /**
     * Set the Limit of the queried Individuals.
     * @param limit int
     */
    public void setLimit(int limit);

    /**
     * Get the permitted amount of Properties.
     * @return Number of Properties.
     */
    //public static int getPropertyCount();

    /**
     * Adding a Variable to the list.
     * @param var String of the variable.
     */
    public void addVariable(String var);

    /**
     * Generates a SELECT Query based on the given properties and
     * returns a String representation of it. The amount of possible
     * properties is limited.
     * @param properties The set of the specified properties.
     * @return A String representation of the SELECT Query.
     */
    public String generateSelectQuery(PropertyIO properties) throws TooManyPropertiesException;

    /**
     * Generates a ASK Query based on the given item name (Q...)
     * and the property (P...). Used for Property Checking.
     * Note: When the statement is deprecated, false is returned!
     * @param item String of form (Q...)
     * @param property String of form (P...)
     * @return ASK Query String
     */
    public String generateAskQuery(String item, String property);


}
