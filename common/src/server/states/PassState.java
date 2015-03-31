package server.states;

import constants.Commands;
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

public class PassState implements StateNode {

    private String mLastRequest;
    private String mLastResponse;

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
        mLastRequest = Commands.PASS;
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
        List<Card> cards = ctr.getServerCards();
        float score = ctr.getServerScore();
        int gain  = ctr.getGain();
        writerManager.writeBankScore(cards, score);
        writerManager.writeGain(gain);
        mLastResponse = Commands.BANK_SCORE + " " + cards.size();
        for (Card c : cards) {
            mLastResponse += c.toString();
        }
        mLastResponse += " " + score + "\n";
        mLastResponse += Commands.GAINS + " " + gain;
    }

    @Override
    public String getLastRequest() {
        return mLastRequest;
    }

    @Override
    public String getLastResponse() {
        return mLastResponse;
    }
}
