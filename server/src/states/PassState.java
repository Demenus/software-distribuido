package states;

import constants.States;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.ComUtilsWriterManager;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateNode;

/**
 * Created by aaron on 13/03/2015.
 */
public class PassState implements StateNode {
    @Override
    public String getState() {
        return States.PASS_STATE;
    }

    @Override
    public boolean isFinalState() {
        return true;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException, TimeOutException {
        return null;
    }

    @Override
    public void checkPreviousState(String previousState) throws StateException {
        if (!previousState.equalsIgnoreCase(States.DRAW_STATE)) {
            throw new StateException(previousState, getState());
        }
    }

    @Override
    public Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException {
        return null;
    }

    @Override
    public void onSuccess(WriterManager writerManager, Object response) throws WriteException, TimeOutException {

    }

    @Override
    public void onError(WriterManager writerManager, ErrType errCode, String message) throws WriteException, TimeOutException {
        ComUtilsWriterManager w = (ComUtilsWriterManager) writerManager;
        w.writeError(errCode, message);
    }
}
