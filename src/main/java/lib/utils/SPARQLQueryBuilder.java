package lib.utils;

/**
 * This is a simple and generic Builder for
 * creating SPARQL Queries.
 * @author Leon Geis
 */
//TODO JavaDoc + Extensions
public class SPARQLQueryBuilder{

    private String query;

    public SPARQLQueryBuilder(){
        //Initialize query
        this.query="";
    }
    public String build(){
        return this.query;
    }
    public SPARQLQueryBuilder prefix(String prefix, String URL){
        this.query += "PREFIX "+prefix+": <"+URL+">\n";
        return this;
    }
    public SPARQLQueryBuilder select(){
        this.query += "SELECT ";
        return this;
    }
    public SPARQLQueryBuilder from(){
        this.query += "FROM ";
        return this;
    }
    public SPARQLQueryBuilder ask(){
        this.query += "ASK ";
        return this;
    }
    public SPARQLQueryBuilder construct(){
        this.query += "CONSTRUCT ";
        return this;
    }
    public SPARQLQueryBuilder distinct(){
        this.query += " DISTINCT ";
        return this;
    }
    public SPARQLQueryBuilder variable(String var){
        this.query += var;
        return this;
    }
    public SPARQLQueryBuilder where(){
        this.query += " WHERE { ";
        return this;
    }
    public SPARQLQueryBuilder subject(String subject){
        this.query += subject+" ";
        return this;
    }
    public SPARQLQueryBuilder predicate(String predicate){
        this.query += predicate+" ";
        return this;
    }
    public SPARQLQueryBuilder object(String object){
        this.query += object+". ";
        return this;
    }
    public SPARQLQueryBuilder end(){
        this.query += "}";
        return this;
    }
    public SPARQLQueryBuilder subquery(String query){
        this.query += "{ "+query+" }";
        return this;
    }
    public SPARQLQueryBuilder bind(String expression, String variable){
        this.query += "("+expression+" AS "+variable+")";
        return this;
    }
    public SPARQLQueryBuilder filter(String expression){
        this.query += "FILTER("+expression+")";
        return this;
    }
    public SPARQLQueryBuilder wikidataServiceLabel(String language){
        this.query += "SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],"+language+"\". }";
        return this;
    }
    public SPARQLQueryBuilder groupBy(String variable){
        this.query += " GROUP BY "+variable;
        return this;
    }
    public SPARQLQueryBuilder having(String expression){
        this.query += "HAVING "+expression;
        return this;
    }
    public SPARQLQueryBuilder limit(int limit){
        this.query += "LIMIT "+limit;
        return this;
    }

}



