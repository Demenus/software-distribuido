package exceptions.protocolexceptions;

import exceptions.BaseException;
import exceptions.ErrType;

/**
 * Created by aaron on 24/02/2015.
 */
public class StateException extends BaseException{
    public StateException(String previousState, String currentState) {
        super(ErrType.STATE_ERROR, "Going from "+previousState+" to "+currentState+" is not allowed");
    }
}
