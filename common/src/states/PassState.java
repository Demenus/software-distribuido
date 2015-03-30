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
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateNode;

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
    public void process(WriterManager writerManager, Object controller, Object parsedMessage) throws ApplicationException, WriteException, TimeOutException {
        GameController ctr = (GameController) controller;
        ctr.playServer();
        writerManager.writeBankScore(ctr.getServerCards(), ctr.getServerScore());
        writerManager.writeGain(ctr.getGain());
    }

    @Override
    public void onError(WriterManager writerManager, ErrType errCode, String message) throws WriteException, TimeOutException {
        writerManager.writeError(errCode, message);
    }
}
