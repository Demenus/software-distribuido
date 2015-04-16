package exceptions.protocolexceptions;

import exceptions.ErrType;
import exceptions.ServerException;

/**
 * Created by aaron on 24/02/2015.
 */
public class StateException extends ServerException {
    public StateException(String previousState, String currentState) {
        super(ErrType.STATE_ERROR, "Going from "+previousState+" to "+currentState+" is not allowed");
    }
}
