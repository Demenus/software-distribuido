package states;

import constants.States;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import gamelayer.GameController;
import io.ComUtilsWriterManager;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateNode;

/**
 * Created by aaron on 26/02/2015.
 */
public class DrawState implements StateNode {
    @Override
    public String getState() {
        return States.DRAW_STATE;
    }

    @Override
    public boolean isFinalState() {
        return false;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException {
        return null;
    }

    @Override
    public void checkPreviousState(String previousState) throws StateException {
        if (!(previousState.equalsIgnoreCase(States.START_STATE) || previousState.equalsIgnoreCase(States.ANTE_STATE))) {
            throw new StateException(previousState, getState());
        }
    }

    @Override
    public Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException {
        GameController ctr = (GameController) controller;
        return ctr.getNextCard();
    }

    @Override
    public void onSuccess(WriterManager writerManager, Object response) throws WriteException {
        ComUtilsWriterManager w = (ComUtilsWriterManager)writerManager;
        w.writeCard((String) response);
    }

    @Override
    public void onError(WriterManager writerManager, ErrType errCode, String message) throws WriteException {
        ComUtilsWriterManager w = (ComUtilsWriterManager)writerManager;
        w.writeError(errCode, message);
    }
}
