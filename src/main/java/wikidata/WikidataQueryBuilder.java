package wikidata;

import org.eclipse.rdf4j.sparqlbuilder.core.Prefix;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.ConstructQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.TriplePattern;

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

//TODO: JavaDoc / Implementation of Class WikidataQueryBuilder (21.10.20)
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
    private static int LIMIT=10;

    /**
     * A List of prefixes for the query.
     * (PREFIX ex:<http://example.org/> ...)
     */
    private List<Prefix> prefixes;

    /**
     * A List of variables for the query.
     * (?s ?p ?o)
     */
    private List<Variable> variables;

    /**
     * A List of TriplePatterns used for the query.
     * {@link TriplePattern}
     */
    private List<TriplePattern> triplePatterns;

    /**
     * A Query of type SELECT.
     */
    private SelectQuery selectQuery;

    /**
     * A Query of type CONSTRUCT.
     */
    private ConstructQuery constructQuery;

    /**
     * The Constructor of the Class.
     */
    public WikidataQueryBuilder(){
        prefixes = new ArrayList<>();
        variables = new ArrayList<>();
        triplePatterns = new ArrayList<>();

        selectQuery = Queries.SELECT();
        constructQuery = Queries.CONSTRUCT();
    }

}
