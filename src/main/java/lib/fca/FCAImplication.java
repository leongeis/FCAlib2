package lib.fca;
/*
 * This library is an enhanced version of the
 * FCAlib
 * @see <a href="https://github.com/julianmendez/fcalib">https://github.com/julianmendez/fcalib</a>
 * @author Leon Geis
 * @version 0.1
 */

import api.fca.Attribute;
import api.fca.Implication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes an Implication of Attributes in
 * a Context.
 * @author Leon Geis
 */
public class FCAImplication<O,A> implements Implication<O,A> {

    /**
     * Premise of an Implication.
     */
    private List<? extends Attribute<O,A>> premise;

    /**
     * Conclusion of an Implication
     */
    private List<? extends Attribute<O,A>> conclusion;

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
    public FCAImplication(List<? extends Attribute<O,A>> newPrem, List<? extends Attribute<O,A>> newCon){
        this.premise = new ArrayList<>(newPrem);
        this.conclusion = new ArrayList<>(newCon);
    }

    /**
     * @return List of Attributes of the Premise
     */
    public List<? extends Attribute<O, A>> getPremise() {
        return premise;
    }

    public void setPremise(List<? extends Attribute<O,A>> premise){
        this.premise=premise;
    }

    public void setConclusion(List<? extends Attribute<O,A>> conclusion){
        this.conclusion=conclusion;
    }

    /**
     * @return List of Attributes of the Conclusion
     */
    public List<? extends Attribute<O, A>> getConclusion() {
        return conclusion;
    }

    /**
     * @return A String representation of the current Implication.
     */
    public String toString(){
        return this.premise.stream().map(Attribute::getAttributeID).collect(Collectors.toList())+"->"+this.conclusion.stream().map(Attribute::getAttributeID).collect(Collectors.toList());
    }

}
