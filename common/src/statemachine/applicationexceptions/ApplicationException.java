package statemachine.applicationexceptions;

import statemachine.protocolexceptions.BaseErrCodeException;

/**
 * Created by aaron on 24/02/2015.
 */
public class ApplicationException extends BaseErrCodeException {

    public ApplicationException(String message, int errCode) {
        super(message, errCode);
    }
}
