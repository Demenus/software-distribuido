package states;

import constants.States;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import gamelayer.GameController;
import io.ComUtilsReaderManager;
import io.ComUtilsWriterManager;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateNode;

/**
 * Created by aaron on 12/03/2015.
 */
public class AnteState implements StateNode {
    @Override
    public String getState() {
        return States.ANTE_STATE;
    }

    @Override
    public boolean isFinalState() {
        return false;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException, TimeOutException {
        ComUtilsReaderManager r = (ComUtilsReaderManager) readerManager;
        return r.readBet();
    }

    @Override
    public void checkPreviousState(String previousState) throws StateException {
        if (!(previousState.equalsIgnoreCase(States.DRAW_STATE))) {
            throw new StateException(previousState, getState());
        }
    }

    @Override
    public void process(WriterManager writerManager, Object controller, Object parsedMessage) throws ApplicationException, WriteException, TimeOutException {
        Integer bet = (Integer) parsedMessage;
        GameController ctr = (GameController) controller;
        ctr.increaseBet(bet);
    }

    @Override
    public void onError(WriterManager writerManager, ErrType errCode, String message) throws WriteException, TimeOutException {
        ComUtilsWriterManager w = (ComUtilsWriterManager) writerManager;
        w.writeError(errCode, message);
    }
}
