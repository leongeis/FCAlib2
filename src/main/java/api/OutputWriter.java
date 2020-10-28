package api;

/**
 * Interface describing Output functionalities.
 * @param <T>
 * @author Leon Geis
 */

public interface OutputWriter<T> {

    /**
     * Print the given Context to the Console.
     * @param t the given context
     */
    void printToConsole(T t);

    /**
     * Write the given Context to a file.
     * @param name name of file
     */
    void writeToFile(T t,String name);

}
