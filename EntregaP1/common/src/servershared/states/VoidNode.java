package servershared.states;

import constants.States;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import servershared.io.ReaderManager;
import servershared.io.WriterManager;
import servershared.statemachine.StateNode;

/**
 * Created by aaron on 24/02/2015.
 */
public class VoidNode implements StateNode {

    @Override
    public String getState() {
        return States.VOID_STATE;
    }

    @Override
    public boolean isFinalState() {
        return false;
    }

    @Override
    public Object parseRequestBody(ReaderManager readerManager) throws ParseException, ReadException {
        return null;
    }

    @Override
    public void checkPreviousState(String previousState) throws StateException {

    }

    @Override
    public void process(WriterManager writerManager, Object controller, Object parsedMessage) throws ApplicationException, WriteException, TimeOutException {

    }

    @Override
    public String getLastRequest() {
        return null;
    }

    @Override
    public String getLastResponse() {
        return null;
    }
}
