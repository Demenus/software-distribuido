package server.statemachine;

import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import server.io.ReaderManager;
import server.io.WriterManager;

/**
 * Created by aaron on 24/02/2015.
 */
public interface StateNode {
    public String getState();

    public boolean isFinalState();

    Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException, TimeOutException;

    void checkPreviousState(String previousState) throws StateException;

    void process(WriterManager writerManager, Object controller, Object parsedMessage) throws ApplicationException, WriteException, TimeOutException;

}
