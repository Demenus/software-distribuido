package server.states;

import constants.States;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import server.gamelayer.GameController;
import server.io.ReaderManager;
import server.io.WriterManager;
import server.statemachine.StateNode;

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
}
