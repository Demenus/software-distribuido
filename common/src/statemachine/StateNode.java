package statemachine;

import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
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

    public boolean isFinalState();

    Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException, TimeOutException;

    void checkPreviousState(String previousState) throws StateException;

    Object process(String previousState, Object controller, Object parsedMessage) throws ApplicationException;

    void onSuccess(WriterManager writerManager, Object response) throws WriteException, TimeOutException;

    public void onError(WriterManager writerManager, ErrType errCode, String message) throws WriteException, TimeOutException;
}
