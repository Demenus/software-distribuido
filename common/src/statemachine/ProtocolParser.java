package statemachine;

import exceptions.connectionexceptions.ReadException;
import exceptions.protocolexceptions.CommandException;
import io.ReaderManager;

/**
 * Created by aaron on 26/02/2015.
 */
public interface ProtocolParser {

    public String getStateFromCommand(ReaderManager readerManager) throws CommandException, ReadException;

}
