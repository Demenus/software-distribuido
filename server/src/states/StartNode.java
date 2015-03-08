package states;

import constants.States;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.ComUtilsWriterManager;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateNode;

/**
 * Created by aaron on 24/02/2015.
 */
public class StartNode implements StateNode {

    @Override
    public String getState() {
        return States.START_STATE;
    }

    @Override
    public boolean isFinalState() {
        return false;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException {
        return null;
    }

    @Override
    public void checkPreviousState(String previousState) throws StateException {
        if (!(previousState.equalsIgnoreCase(States.VOID_STATE))) {
            throw new StateException(previousState, getState());
        }
    }

    @Override
    public Object process(String previousState, Object controller, Object request) throws ApplicationException {
        return 101;
    }

    @Override
    public void onSuccess(WriterManager writerManager, Object response) throws WriteException {
        ComUtilsWriterManager w = (ComUtilsWriterManager) writerManager;
        w.writeStartBet((Integer) response);
    }

    @Override
    public void onError(WriterManager writerManager, ErrType errCode, String message) throws WriteException {
        ComUtilsWriterManager w = (ComUtilsWriterManager) writerManager;
        w.writeError(errCode, message);
    }
}
