package game.states;

import exceptions.ErrType;
import exceptions.connectionexceptions.WriteException;
import game.Commands;
import statemachine.StateNode;
import comutils.ComUtils;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.protocolexceptions.ParseException;
import game.States;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public class StartNode implements StateNode {

    private static final int ERR_IO = 0;
    private static final int ERR_PARSE = 1;

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
        return previousState.equalsIgnoreCase(States.VOID_STATE);
    }

    @Override
    public Object process(String previousState, Object controller, Object request) throws ApplicationException {
        return 100;
    }

    @Override
    public void onSuccess(OutputStream responseStream, Object response) throws WriteException {
        Integer i = (Integer)response;
        ComUtils.Writer writer = new ComUtils.Writer(responseStream);
        try {
            writer.write_string(Commands.START_BET);
            writer.write_char(' ');
        } catch (IOException e) {
            e.printStackTrace();
            throw new WriteException();
        }

        //writer.write_int32(i);
    }

    @Override
    public void onError(OutputStream responseStream, ErrType errCode, String message)  {
        ComUtils.Writer writer = new ComUtils.Writer(responseStream);

    }
}
