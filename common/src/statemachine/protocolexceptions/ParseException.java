package statemachine.protocolexceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public class ParseException extends BaseErrCodeException {
    public ParseException(String message, int errCode) {
        super(message, errCode);
    }
}
