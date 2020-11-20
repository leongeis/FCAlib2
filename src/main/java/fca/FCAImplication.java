package fca;
/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import java.util.ArrayList;
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
    private List<FCAAttribute<O,A>> conclusion;

    /**
     * Constructor of the class.
     */
    public FCAImplication(){
        this.premise = new ArrayList<>();
        this.conclusion = new ArrayList<>();
    }

    /**
     * Constructor of the class, which creates copies of the
     * parameters and sets them accordingly.
     * @param newPrem Premise, List of Attributes
     * @param newCon Conclusion, List of Attributes
     */
    public FCAImplication(List<FCAAttribute<O,A>> newPrem, List<FCAAttribute<O,A>> newCon){
        this.premise = new ArrayList<>(newPrem);
        this.conclusion = new ArrayList<>(newCon);
    }

    public List<FCAAttribute<O, A>> getPremise() {
        return premise;
    }

    public List<FCAAttribute<O, A>> getConclusion() {
        return conclusion;
    }

    /**
     * @return A String representation of the current Implication.
     */
    public String toString(){
        return this.premise+"->"+this.conclusion;
    }

}
