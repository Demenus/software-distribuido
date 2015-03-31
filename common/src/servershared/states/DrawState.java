package servershared.states;

import constants.Commands;
import constants.States;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import servershared.gamelayer.GameController;
import servershared.gamelayer.model.Card;
import servershared.io.ReaderManager;
import servershared.io.WriterManager;
import servershared.statemachine.StateNode;

import java.util.List;

public class DrawState implements StateNode {

    private boolean mFinalState = false;
    private String mLastRequest;
    private String mLastResponse;

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
        mLastRequest = Commands.DRAW;
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
            mLastResponse  = Commands.CARD+" "+card.toString() + "\n";
            mLastResponse += Commands.BUSTING + "\n";
            mLastResponse += Commands.BANK_SCORE + " " + cards.size();
            for (Card c : cards) {
                mLastResponse += c.toString();
            }
            mLastResponse += " " + score + "\n";
            mLastResponse += Commands.GAINS + " " + gain;
        } else {
            writerManager.writeCard(card);
            mLastResponse = Commands.CARD+" "+card.toString();
        }
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
