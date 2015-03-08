package statemachine;

import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.Reader;
import io.Writer;

/**
 * Created by aaron on 24/02/2015.
 */
public interface StateNode {
    public String getState();

    Object parseRequestBody(Reader reader) throws ParseException, ReadException;

    void checkPreviousState(String previousState) throws StateException;

    Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException;

    void onSuccess(Writer responseStream, Object response) throws WriteException;

    public void onError(Writer responseStream, ErrType errCode, String message) throws WriteException;
}
