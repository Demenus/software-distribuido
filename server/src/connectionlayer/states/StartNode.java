package connectionlayer.states;

import comutils.ComUtils;
import connectionlayer.States;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import server.ServerProtocolParser;
import statemachine.StateNode;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public class StartNode implements StateNode {

    @Override
    public String getState() {
        return States.START_STATE;
    }

    @Override
    public Object parseRequestBody(InputStream messageStream) throws ParseException {
        return null;
    }

    @Override
    public boolean checkPreviousState(String previousState) {
        return previousState.equalsIgnoreCase(States.VOID_STATE) || previousState.equalsIgnoreCase(States.START_STATE);
    }

    @Override
    public Object process(String previousState, Object controller, Object request) throws ApplicationException {
        return 100;
    }

    @Override
    public void onSuccess(OutputStream responseStream, Object response) throws WriteException {
        ServerProtocolParser parser = new ServerProtocolParser();
        Integer i = (Integer)response;
        parser.writeStartBet(responseStream, i);
    }

    @Override
    public void onError(OutputStream responseStream, ErrType errCode, String message)  {
        ComUtils.Writer writer = new ComUtils.Writer(responseStream);

    }
}
