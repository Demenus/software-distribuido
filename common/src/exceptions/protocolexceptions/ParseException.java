package exceptions.protocolexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 24/02/2015.
 */
public class ParseException extends ServerException {
    public ParseException(String message) {
        super(ErrType.PARSE_ERROR, message);
    }
}
