package exceptions.protocolexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 24/02/2015.
 */
public class CommandException extends ServerException {

    public CommandException() {
        super(ErrType.COMMAND_ERROR, "The command is not recognized");
    }

    public CommandException(String command) {
        super(ErrType.COMMAND_ERROR, "The command '"+command+"' is not a valid token");
    }
}
