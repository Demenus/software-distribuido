package statemachine;

import exceptions.connectionexceptions.ReadException;
import exceptions.protocolexceptions.CommandException;
import io.Reader;

import java.util.Map;

/**
 * Created by aaron on 26/02/2015.
 */
public interface ProtocolParser {

    public String getStateFromCommand(Reader reader, Map<String, String> mapCommands) throws CommandException, ReadException;

}
