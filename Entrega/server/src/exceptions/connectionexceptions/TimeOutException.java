package exceptions.connectionexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 12/03/2015.
 */
public class TimeOutException extends ServerException {
    public TimeOutException() {
        super(ErrType.TIMEOUT_ERROR, "Connection without activity, close");
    }
}
