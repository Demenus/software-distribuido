package statemachine;

import statemachine.applicationexceptions.ApplicationException;
import statemachine.protocolexceptions.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public interface StateNode {
    Object parseRequestBody(InputStream messageStream) throws ParseException;

    boolean checkPreviousState(String previousState);

    Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException;

    void onSuccess(OutputStream responseStream, Object response) throws IOException;

    public void onError(OutputStream responseStream, int errCode, String message) throws IOException;
}
