package utils.exceptions;

/**
 * Exception used when no properties are defined/ no property
 * file found.
 * @author Leon Geis
 */
public class NoPropertiesDefinedException extends Exception {

    public NoPropertiesDefinedException(String msg){super(msg);}

}
