package api;

/**
 * Interface describing Output functionalities,
 * which can either be printing to the Console or
 * saving the results in a file in utils.output.
 * Note: The file path cannot be changed and
 * all files created by these methods are
 * created there.
 * @author Leon Geis
 */
//TODO create static methods
public interface OutputWriter {

    /**
     * Print the given Context to the Console.
     * @param t the given context
     */
    <T extends Context<O,A>,O,A> void printCrosstableToConsole(T t);

    /**
     * Write the given Context to a file.
     * @param name name of file
     */
    <T extends Context<O,A>,O,A> void writeCrosstableToFile(T t, String name);

    /**
     * Print all Concepts of the given Context
     * to the Console.
     * @param t the given Context
     */
    <T extends Context<O,A>,O,A> void printConceptsToConsole(T t);

    /**
     * Write all Concepts of the given Context to
     * a file.
     * @param t the given context
     * @param name name of file
     */
    <T extends Context<O,A>,O,A> void writeConceptsToFile(T t, String name);

    /**
     * Displays all Implications of the Stem Base of the Context
     * on the console.
     * @param t The Context from which the Stem Base will be computed
     */
    <T extends Context<O,A>,O,A> void printStemBaseToConsole(T t);
    /**
     * Writes all Implications of the Stem Base of the given Context to the specific file.
     * @param t The Context from the Stem Base will be computed.
     * @param name The Name of the File ("example.txt")
     */
    <T extends Context<O,A>,O,A> void writeStemBaseToFile(T t, String name);

}
