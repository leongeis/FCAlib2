package utils.exceptions;

/**
 * Exception used for the case no variables are given in a Query.
 * @author Leon Geis
 */
public class NoVariablesException extends Exception{

    public NoVariablesException(String msg){
        super(msg);
    }

}
