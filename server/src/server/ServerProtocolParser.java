package server;

import connectionlayer.io.ComutilsReader;
import exceptions.connectionexceptions.ReadException;
import exceptions.protocolexceptions.CommandException;
import statemachine.ProtocolParser;

import java.util.Map;

/**
 * Created by aaron on 26/02/2015.
 */
public class ServerProtocolParser implements ProtocolParser {
    @Override
    public String getStateFromCommand(ComutilsReader reader, Map<String, String> mapCommands) throws CommandException, ReadException {
        //We read the command


        //Check if the command is in the available command list
        String state = null;
        for (Map.Entry<String, String> stateCommandPair : mapCommands.entrySet()) {
            if (command.equalsIgnoreCase(stateCommandPair.getValue())) {
                state = stateCommandPair.getKey();
            }
        }
        if (state == null)
            throw new CommandException(command);
        return state;
    }
}
