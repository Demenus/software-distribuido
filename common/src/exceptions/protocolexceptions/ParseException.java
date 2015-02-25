package exceptions.protocolexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 24/02/2015.
 */
public class ParseException extends BaseException {
    public ParseException(String message) {
        super(ErrType.PARSE_ERROR, message);
    }
}
