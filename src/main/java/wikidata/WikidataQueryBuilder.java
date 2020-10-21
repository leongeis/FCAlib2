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

/**
 * @author Leon Geis
 */


//For more information on SPARQL Queries, consider: https://www.w3.org/2009/Talks/0615-qbe/

//TODO: JavaDoc / Implementation of Class WikidataQueryBuilder (21.10.20)

public class WikidataQueryBuilder {

    private List<Prefix> prefixes;

    private List<Variable> variables;

    private List<TriplePattern> triplePatterns;

    private SelectQuery selectQuery;

    private ConstructQuery constructQuery;

    public WikidataQueryBuilder(){
        prefixes = new ArrayList<>();
        variables = new ArrayList<>();
        triplePatterns = new ArrayList<>();

        selectQuery = Queries.SELECT();
        constructQuery = Queries.CONSTRUCT();
    }

}
