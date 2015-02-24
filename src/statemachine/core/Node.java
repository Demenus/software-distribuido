package statemachine.core;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public interface Node {
    String getState();
    Map<String, Object> parseMessage(InputStream messageStream) throws ParseException;
    boolean checkPreviousState(String previousState);
    //TODO: This should throw some kind of game exception from controller
    String getNextState(String previousState, Controller controller, Map<String, Object> parsedMessage);
    //TODO: This should return an action to do
    void onError(String previousState, InputStream messageStream);
}
