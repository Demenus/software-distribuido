package exceptions.connectionexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 24/02/2015.
 */
public class ReadException extends ServerException {
    public ReadException() {
        super(ErrType.READ_ERROR, "Can't read from InputStream ");
    }
}
