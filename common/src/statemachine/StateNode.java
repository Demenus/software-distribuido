package statemachine;

import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.ReaderManager;
import io.WriterManager;

/**
 * Created by aaron on 24/02/2015.
 */
public interface StateNode {
    public String getState();

    Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException;

    void checkPreviousState(String previousState) throws StateException;

    Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException;

    void onSuccess(WriterManager responseStream, Object response) throws WriteException;

    public void onError(WriterManager responseStream, ErrType errCode, String message) throws WriteException;
}
