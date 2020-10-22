/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */


import java.util.List;

/**
 * Describes a single Formal Context with
 * the corresponding Objects and Attributes.
 * @author Leon Geis
 * TODO:INCIDENCE RELATION ?
 */

public class FormalContext {

    /**
     * Name of the Context.
     */
    private String contextName;

    /**
     * List of all Objects of the Context.
     */
    private List<FCAObject> contextObjects;

    /**
     * List of all Attributes of the Context.
     */
    private List<FCAAttribute> contextAttributes;


}
