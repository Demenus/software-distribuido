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
import servershared.io.ReaderManager;
import servershared.io.WriterManager;
import servershared.statemachine.StateNode;

/**
 * Created by aaron on 12/03/2015.
 */
public class AnteState implements StateNode {

    private String mLastRequest;
    private int mState = 0;

    @Override
    public String getState() {
        return States.ANTE_STATE;
    }

    @Override
    public boolean isFinalState() {
        return false;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException{
        int bet = 0;
        if (mState == 0) {
            try {
                readerManager.readChar();
                mState = 1;
            } catch (ReadException e) {
                throw new ParseException();
            } catch (TimeOutException e) {
                throw new ParseException();
            }
        }
        if (mState == 1) {
            try {
                bet = readerManager.readInt32();
                mState = 0;
            } catch (ReadException e) {
                throw new ParseException();
            } catch (TimeOutException e) {
                throw new ParseException();
            }
            mLastRequest = Commands.ANTE+" "+bet;
        }
        return bet;
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
    public String getLastRequest() {
        return mLastRequest;
    }

    @Override
    public String getLastResponse() {
        return null;
    }
}
