package game.states;

import game.Commands;
import statemachine.StateNode;
import statemachine.comutils.ComUtils;
import statemachine.applicationexceptions.ApplicationException;
import statemachine.protocolexceptions.ParseException;
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
    public Object parseRequestBody(InputStream messageStream) throws ParseException {
        return null;
    }

    @Override
    public boolean checkPreviousState(String previousState) {
        return previousState.equalsIgnoreCase(States.VOID_STATE);
    }

    @Override
    public Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException {
        return 100;
    }

    @Override
    public void onSuccess(OutputStream responseStream, Object response) throws IOException {
        Integer i = (Integer)response;
        ComUtils.Writer writer = new ComUtils.Writer(responseStream);
        writer.write_string(Commands.START_BET);
        writer.write_char(' ');
        //writer.write_int32(i);
    }

    @Override
    public void onError(OutputStream responseStream, int errCode, String message) throws IOException {
        ComUtils.Writer writer = new ComUtils.Writer(responseStream);
        switch (errCode) {
            case ERR_IO:
                writer.write_string(Commands.ERROR);
                writer.write_string_variable(message.length(), message);
                break;
            case ERR_PARSE:
                break;
        }
    }
}
