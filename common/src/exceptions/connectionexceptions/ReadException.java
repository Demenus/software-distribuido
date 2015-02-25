package exceptions.connectionexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 24/02/2015.
 */
public class ReadException extends BaseException {
    public ReadException() {
        super(ErrType.READ_ERROR, "Can't read from InputStream ");
    }
}
