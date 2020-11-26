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

    /**
     * Displays all Implications of the Stem Base of the Context
     * on the console.
     * @param t The Context from which the Stem Base will be computed
     */
    void printStemBaseToConsole(T t);
    /**
     * Writes all Implications of the Stem Base of the given Context to the specific file.
     * @param t The Context from the Stem Base will be computed.
     * @param name The Name of the File ("example.txt")
     */
    void writeStemBaseToFile(T t, String name);

}
