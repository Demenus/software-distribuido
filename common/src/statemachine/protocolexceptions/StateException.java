package statemachine.protocolexceptions;

/**
 * Created by aaron on 24/02/2015.
 */
public class StateException extends Exception{
    public StateException(String previousState, String currentState) {
        super("Going from "+previousState+" to "+currentState+" is not allowed");
    }
}
