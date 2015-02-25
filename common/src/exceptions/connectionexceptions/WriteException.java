package exceptions.connectionexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 24/02/2015.
 */
public class WriteException extends BaseException {
    public WriteException() {
        super(ErrType.WRITE_ERROR, "Can't write on OutputStream");
    }
}
