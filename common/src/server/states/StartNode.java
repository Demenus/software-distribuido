package server.states;

import constants.States;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import server.gamelayer.GameController;
import server.io.ReaderManager;
import server.io.WriterManager;
import server.statemachine.StateNode;

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
    public void process(WriterManager writerManager, Object controller, Object request) throws ApplicationException, WriteException, TimeOutException {
        GameController gameController = (GameController) controller;
        int bet = gameController.getBet();
        writerManager.writeStartBet(bet);
    }
}
