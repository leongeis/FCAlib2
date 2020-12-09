package api;

import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.TupleQueryResult;

/**
 * Interface describing the access to Wikidata,
 * via the SPARQL-Endpoint.
 * Note: The implementation of this interface makes
 * extensive use of the RDF4J library to connect
 * to the SPARQL-Endpoint of Wikidata. Hence, a lot
 * of the return types being Objects from that library.
 * For more information on the RDF4J library,
 * see <a href="https://rdf4j.org/documentation/">https://rdf4j.org/documentation/</a>
 * @author Leon Geis
 */
public interface WikidataAccess {

    /**
     * Set URL of SPARQL Endpoint.
     * @param sparqlEndpoint String of the URL Endpoint.
     */
    void setSparqlEndpoint(String sparqlEndpoint);

    /**
     * Get currently used SPARQL Endpoint URL.
     * @return URL as a String.
     */
    String getSparqlEndpoint();

    /**
     * Sets SPARQL Repository and starts connection.
     */
    void establishConnection();

    /**
     * Performs a CONSTRUCT Query on the Wikidata Knowledge Graph.
     *
     * @param query SPARQL Query as String Object.
     * @return If no Exception occurred: Object of type {@link org.eclipse.rdf4j.query.GraphQueryResult}, otherwise <code>null</code>
     */
    GraphQueryResult constructQuery(String query);

    /**
     * Performs a SELECT Query on the Wikidata Knowledge Graph.
     *
     * @param query SPARQL Query as String Object.
     * @return If no Exception occurred: Object of type {@link org.eclipse.rdf4j.query.TupleQueryResult}, otherwise <code>null</code>
     */
    TupleQueryResult selectQuery(String query);

    /**
     * Performs a ASK Query on the Wikidata Knowledge Graph.
     *
     * @param query SPARQL Query as String Object.
     * @return If no Exception occured: Either true or false, otherwise <code>null</code>
     */
    Boolean booleanQuery(String query);

}
