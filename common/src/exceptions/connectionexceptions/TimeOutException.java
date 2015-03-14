package exceptions.connectionexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 12/03/2015.
 */
public class TimeOutException extends BaseException {
    public TimeOutException() {
        super(ErrType.TIMEOUT_ERROR, "Connection without activity, close");
    }
}
