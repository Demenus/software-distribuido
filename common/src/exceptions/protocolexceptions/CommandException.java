package exceptions.protocolexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 24/02/2015.
 */
public class CommandException extends BaseException {
    public CommandException(String command) {
        super(ErrType.COMMAND_ERROR, "The command '"+command+"' is not a valid token");
    }
}
