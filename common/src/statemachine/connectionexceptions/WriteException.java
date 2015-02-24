package statemachine.connectionexceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public class WriteException extends Exception {
    public WriteException() {
        super("Can't write on OutputStream");
    }
}
