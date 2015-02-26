package connectionlayer.states;

import connectionlayer.States;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import statemachine.StateNode;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 26/02/2015.
 */
public class DrawState implements StateNode {
    @Override
    public String getState() {
        return States.DRAW_STATE;
    }

    @Override
    public Object parseRequestBody(InputStream messageStream) throws ParseException, ReadException {
        return null;
    }

    @Override
    public boolean checkPreviousState(String previousState) throws StateException {
        return previousState.equalsIgnoreCase(States.START_STATE) || previousState.equalsIgnoreCase(States.ANTE_STATE);
    }

    @Override
    public Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException {
        return null;
    }

    @Override
    public void onSuccess(OutputStream responseStream, Object response) throws WriteException {

    }

    @Override
    public void onError(OutputStream responseStream, ErrType errCode, String message) throws WriteException {

    }
}
