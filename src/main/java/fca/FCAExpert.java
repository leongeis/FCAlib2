package fca;

import de.tudresden.inf.tcs.fcaapi.FCAImplication;
import de.tudresden.inf.tcs.fcaapi.FCAObject;
import de.tudresden.inf.tcs.fcalib.AbstractExpert;

/**
 * Implementation of an Expert for a Formal Context.
 * @author Leon Geis
 */
//TODO Expert Implementation
public class FCAExpert<O,A> extends AbstractExpert<A,O,FCAObject<A,O>> {
    @Override
    public void requestCounterExample(FCAImplication fcaImplication) {

    }

    @Override
    public void askQuestion(FCAImplication fcaImplication) {

    }

    @Override
    public void counterExampleInvalid(FCAObject fcaObject, int i) {

    }

    @Override
    public void explorationFinished() {

    }

    @Override
    public void forceToCounterExample(FCAImplication fcaImplication) {

    }

    @Override
    public void implicationFollowsFromBackgroundKnowledge(FCAImplication fcaImplication) {

    }
}
