package fca;
/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import java.util.List;

/**
 * Describes an Implication of Attributes in
 * a Context.
 * @author Leon Geis
 */
public class FCAImplication<O,A> {

    /**
     * Premise of an Implication.
     */
    private List<FCAAttribute<O,A>> premise;

    /**
     * Conclusion of an Implication
     */
    private List<FCAObject<O,A>> conclusion;

}
