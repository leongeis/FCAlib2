package api;

/**
 * Interface describing a Context in FCA.
 * @author Leon Geis
 */

public interface Context {

    <T extends ClosureOperator<?>> T getEntity();

}
