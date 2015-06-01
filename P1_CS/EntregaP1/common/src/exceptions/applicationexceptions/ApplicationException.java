package exceptions.applicationexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 24/02/2015.
 */
public class ApplicationException extends ServerException {

    public ApplicationException(String message) {
        super(ErrType.APPLICATION_ERROR, message);
    }
}
