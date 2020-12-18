package api.utils;

import java.util.List;

/**
 * Interface describing a SPARQL Query Generator for Wikidata.
 * This interface can be used to generate a SPARQL Query,
 * specifically for Wikidata. The resulting Query will
 * always be represented as a String.
 * Note: One can also write own Queries and save them
 * as a String. For more information on how to write own
 * SPARQL Queries, see <a href="https://www.w3.org/TR/rdf-sparql-query/">https://www.w3.org/TR/rdf-sparql-query/</a>
 * @author Leon Geis
 */
//TODO JavaDoc + Builder Pattern
public interface SPARQLQueryGenerator {

    String prefixes =
            "PREFIX sc: <http://purl.org/science/owl/sciencecommons/>\n"+
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
            "PREFIX schema: <http://schema.org/>\n"+
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
            "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"+
            "PREFIX wd: <http://www.wikidata.org/entity/>\n"+
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"+
            "PREFIX dbp: <http://dbpedia.org/property/>\n"+
            "PREFIX dbr: <http://dbpedia.org/resource/>\n"+
            "PREFIX dbo: <http://dbpedia.org/ontology/>\n"+
            "PREFIX yago: <http://yago-knowledge.org/resource/>\n";


    static String generateSelectUnionQuery(List<String> properties, int limit){
        //Create String to be returned at the end
        String query = "";
        //First add the prefixes
        query += prefixes;
        //Add the Head of the query
        query += "SELECT DISTINCT * WHERE {";
        //Iterate over each property and add it to the query
        int i=1;
        for(String prop : properties){
            if(i == properties.size()){
                query += "{ ?s <"+prop+"> ?o.}} ";
            }else {
                query += "{ ?s <"+prop+"> ?o.} UNION ";
            }
            i++;
        }
        //At the end check if limit is specified
        if(limit>0){
            query += "LIMIT "+limit;
        }
        //Return generated query
        return query;
    }

    static String generateAskQuery(String entity, String property){
        return prefixes+"ASK WHERE {<"+entity+"> <"+property+"> ?o.}";
    }

    static String generateSelectWikidataInstanceOfQuery(String propertyClass){
        return prefixes+"select * where {?s wdt:P31 <" + propertyClass + ">.}";
    }

    static String generateSelectPropertyCheckQuery(String entity, List<String> properties){
        //Create String to be returned
        String query = prefixes+"";
        //Add first part of the query
        query += "select * where{ <";
        //Add entity
        query += entity+"> ?p ?o. ";
        //Add BIND constraint
        query += "BIND ((";
        //For each property add the corresponding constraint
        for(String prop : properties){
            //If the current String is the last element add additional end of query
            if(properties.indexOf(prop)==properties.size()-1){
                query += "?p=<"+prop+">) AS ?result) FILTER (?result=true)}";
            }else {
                query += "?p=<" + prop + "> || ";
            }
        }
        return query;
    }

    static String generateSelectPropertyCheckQuery(List<String> properties){
        //Create String to be returned
        String query = prefixes+"";
        //Add first part of the query
        query += "select * where{ ?s owl:equivalentProperty ?o.";
        //Add BIND constraint
        query += "BIND ((";
        //For each property add the corresponding constraint
        for(String prop : properties){
            //If the current String is the last element add additional end of query
            if(properties.indexOf(prop)==properties.size()-1){
                query += "?o=<"+prop+">) AS ?result) FILTER (?result=true)}";
            }else {
                query += "?o=<" + prop + "> || ";
            }
        }
        return query;
    }

    static String generateSelectSameAsQuery(String entity){
        return "SELECT * WHERE { ?s owl:sameAs <"+entity+">.}";
    }

    static String generateSelectEquivalentPropertyQuery(String property){
        return prefixes+"SELECT * WHERE {?s owl:equivalentProperty <"+property+"> .}";
    }

    static String generateSelectWikidataEquivalentPropertyQuery(String property){
        //wdt:P1628 is equivalent to owl:equivalentProperty
        return "SELECT * WHERE { <"+property+"> wdt:P1628 ?o.}";
    }

}
