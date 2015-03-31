package exceptions.connectionexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 24/02/2015.
 */
public class WriteException extends ServerException {
    public WriteException() {
        super(ErrType.WRITE_ERROR, "Can't write on OutputStream");
    }
}
