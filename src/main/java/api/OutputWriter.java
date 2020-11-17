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
    void printCrosstableToConsole(T t);

    /**
     * Write the given Context to a file.
     * @param name name of file
     */
    void writeCrosstableToFile(T t, String name);

    /**
     * Print all Concepts of the given Context
     * to the Console.
     * @param t the given Context
     */
    void printConceptsToConsole(T t);

    /**
     * Write all Concepts of the given Context to
     * a file.
     * @param t the given context
     * @param name name of file
     */
    void writeConceptsToFile(T t, String name);

}
