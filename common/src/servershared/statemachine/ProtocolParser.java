package servershared.statemachine;

import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.protocolexceptions.CommandException;
import servershared.io.ReaderManager;

/**
 * Created by aaron on 26/02/2015.
 */
public interface ProtocolParser {

    public String getStateFromCommand(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException;

}
