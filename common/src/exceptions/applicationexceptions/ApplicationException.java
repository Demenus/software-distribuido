package exceptions.applicationexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 24/02/2015.
 */
public class ApplicationException extends BaseException {

    public ApplicationException(String message) {
        super(ErrType.APPLICATION_ERROR, message);
    }
}
