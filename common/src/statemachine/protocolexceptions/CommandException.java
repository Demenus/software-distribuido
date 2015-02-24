package statemachine.protocolexceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public class CommandException extends Exception {
    public CommandException(String command) {
        super("The command '"+command+"' is not a valid token");
    }
}
