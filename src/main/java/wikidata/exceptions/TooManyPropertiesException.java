package wikidata.exceptions;

/**
 * Exception used when the amount of the given properties
 * is above the specified limit.
 * @author Leon Geis
 */
public class TooManyPropertiesException extends Exception {

    public TooManyPropertiesException(String msg){
        super(msg);
    }

}
