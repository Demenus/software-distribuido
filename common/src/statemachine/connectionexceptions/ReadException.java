package statemachine.connectionexceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public class ReadException extends Exception {
    public ReadException() {
        super("Can't read from InputStream ");
    }
}
