package wikidata;

import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */


/**
 * Provides basic data extraction methods for the Wikidata Knowledge Graph.
 * <a href="https://www.wikidata.org/wiki/Wikidata:Main_Page">wikidata.org/</a>
 * @author Leon Geis
 * Frankfurt University of Applied Sciences
 * lgeis@stud.fra-uas.de
 */
public class WikidataExtraction {

    /**
     * URL for the used SPARQL Endpoint.
     */
    private String sparqlEndpoint="https://query.wikidata.org/sparql";
    /**
     * Repository Object of Wikidata.
     */
    private Repository wikiRepo;
    /**
     * Connection of this Extraction.
     */
    private RepositoryConnection wikiconn;

    /**
     * Constructor which sets Basic Logging to Console.
     */
    public WikidataExtraction(){
        //Set Up Basic Logging to Console
        org.apache.log4j.BasicConfigurator.configure();
    }

    /**
     * Set URL of SPARQL Endpoint.
     * @param sparqlEndpoint String of the URL Endpoint.
     */
    public void setSparqlEndpoint(String sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }

    /**
     * Get currently used SPARQL Endpoint URL.
     * @return URL as a String.
     */
    public String getSparqlEndpoint() {
        return sparqlEndpoint;
    }

    /**
     * Sets SPARQL Repository and starts connection.
     */
    public void establishConnection(){
        //Create Repository Object with SPARQL Endpoint
        this.wikiRepo = new SPARQLRepository(sparqlEndpoint);

        //Create Connection Object to the specified Repository
        this.wikiconn = wikiRepo.getConnection();
    }

    /**
     * Performs a CONSTRUCT Query on the Wikidata Knowledge Graph.
     *
     * @param query SPARQL Query as String Object.
     * @return If no Exception occurred: Object of type {@link org.eclipse.rdf4j.query.GraphQueryResult}, otherwise <code>null</code>
     *
     */
    public GraphQueryResult constructQuery(String query){
        try{
            //Return result of the specified SPARQL Query
            return this.wikiconn.prepareGraphQuery(query).evaluate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Performs a SELECT Query on the Wikidata Knowledge Graph.
     *
     * @param query SPARQL Query as String Object.
     * @return If no Exception occurred: Object of type {@link org.eclipse.rdf4j.query.TupleQueryResult}, otherwise <code>null</code>
     *
     */
    public TupleQueryResult selectQuery(String query){
        try{
            //Return result of the specified SPARQL Query
            return this.wikiconn.prepareTupleQuery(query).evaluate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Performs a ASK Query on the Wikidata Knowledge Graph.
     *
     * @param query SPARQL Query as String Object.
     * @return If no Exception occured: Either true or false, otherwise <code>null</code>
     */
    public Boolean booleanQuery(String query){
        try{
            return this.wikiconn.prepareBooleanQuery(query).evaluate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
