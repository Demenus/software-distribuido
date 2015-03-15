package server;

import constants.Commands;
import constants.States;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.protocolexceptions.CommandException;
import io.ComUtilsReaderManager;
import io.ReaderManager;
import statemachine.ProtocolParser;


public class GameProtocolParser implements ProtocolParser {
    @Override
    public String getStateFromCommand(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException {
        //We read the command
        ComUtilsReaderManager re = (ComUtilsReaderManager) readerManager;
        String command = re.readCommand();

        if (command.equalsIgnoreCase(Commands.START)) {
            return States.START_STATE;
        } else if (command.equalsIgnoreCase(Commands.DRAW)) {
            return States.DRAW_STATE;
        } else if (command.equalsIgnoreCase(Commands.ANTE)) {
            return States.ANTE_STATE;
        } else if (command.equalsIgnoreCase(Commands.PASS)) {
            return States.PASS_STATE;
        } else {
            throw new CommandException(command);
        }
    }
}
