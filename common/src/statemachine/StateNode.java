package statemachine;

import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aaron on 24/02/2015.
 */
public interface StateNode {
    public String getState();

    Object parseRequestBody(InputStream messageStream) throws ParseException, ReadException;

    boolean checkPreviousState(String previousState) throws StateException;

    Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException;

    void onSuccess(OutputStream responseStream, Object response) throws WriteException;

    public void onError(OutputStream responseStream, ErrType errCode, String message) throws WriteException;
}
