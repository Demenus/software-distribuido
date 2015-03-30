package server.states;

import constants.States;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import server.gamelayer.GameController;
import server.gamelayer.model.Card;
import server.io.ReaderManager;
import server.io.WriterManager;
import server.statemachine.StateNode;

import java.util.List;

public class DrawState implements StateNode {

    private boolean mFinalState = false;

    @Override
    public String getState() {
        return States.DRAW_STATE;
    }

    @Override
    public boolean isFinalState() {
        return mFinalState;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException {
        return null;
    }

    @Override
    public void checkPreviousState(String previousState) throws StateException {
        if (!(previousState.equalsIgnoreCase(States.START_STATE) || previousState.equalsIgnoreCase(States.ANTE_STATE) || previousState.equalsIgnoreCase(States.DRAW_STATE))) {
            throw new StateException(previousState, getState());
        }
    }

    @Override
    public void process(WriterManager writerManager, Object controller, Object parsedMessage) throws ApplicationException, WriteException, TimeOutException {
        GameController ctr = (GameController) controller;
        Card card = ctr.getUserNextCard();
        if (ctr.userHasLost()) {
            mFinalState = true;
            ctr.playServer();
            List<Card> cards = ctr.getServerCards();
            float score = ctr.getServerScore();
            int gain = ctr.getGain();
            writerManager.writeCard(card);
            writerManager.writeBusting();
            writerManager.writeBankScore(cards, score);
            writerManager.writeGain(gain);
        } else {
            writerManager.writeCard(card);
        }
    }
}
